package bettinger.david.taskchainapp.data.repository


import android.util.Log
import bettinger.david.taskchainapp.data.db.TaskChainDatabase
import bettinger.david.taskchainapp.data.entity.CommentEntity
import bettinger.david.taskchainapp.data.entity.TaskChainEntity
import bettinger.david.taskchainapp.data.entity.TaskEntity
import bettinger.david.taskchainapp.data.entity.UserEntity
import bettinger.david.taskchainapp.data.mapper.TaskChainMapper
import bettinger.david.taskchainapp.data.mapper.UserMapper
import bettinger.david.taskchainapp.data.net.TaskChainService
import bettinger.david.taskchainapp.data.net.UserService
import bettinger.david.taskchainapp.usecase.scheduler.BaseSchedulerProvider
import bettinger.david.taskchainapp.utils.Cache
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class TaskChainRepositoryImpl @Inject constructor(
    private val taskChainDatabase: TaskChainDatabase,
    private val taskChainService: TaskChainService,
    private val userService: UserService,
    private val userMapper: UserMapper,
    private val schedulerProvider: BaseSchedulerProvider,
    private val taskChainMapper: TaskChainMapper
) :
    TaskChainRepository {

    private val TAG = "Repository"

    private val userEntityDao = taskChainDatabase.userEntityDao()
    private val commentEntityDao = taskChainDatabase.commentEntityDao()
    private val taskChainEntityDao = taskChainDatabase.taskChainEntityDao()
    private val taskEntityDao = taskChainDatabase.taskEntityDao()

    //TODO clean disposable at the end
    private val compositeDisposable = CompositeDisposable()

    override fun getAllTaskChains(): Observable<List<TaskChainEntity>> {
        return taskChainEntityDao.getAll()
    }

    //TODO refactor to get feedback about the completion of loadDataFromApi and cacheData methods
    override fun loadDataFromApi() {
        taskChainService.getAllTaskChains()
            .observeOn(schedulerProvider.ui())
            .subscribeOn(schedulerProvider.io())
            .map { taskChainMapper.mapToEntityList(it) as MutableList<TaskChainEntity> }
            .subscribe(this::cacheData)
            .addTo(compositeDisposable)
        //TODO handle on error
    }

    //TODO handle update correctly
    private fun cacheData(taskChainEntities: List<TaskChainEntity>) {
        val insertCompletables = mutableListOf<Completable>()
        for (taskChainEntity in taskChainEntities) {
            insertCompletables.add(taskChainEntityDao.insert(taskChainEntity))
            insertCompletables.add(saveNewUser(taskChainEntity.createdByUser))
            insertCompletables.add(taskEntityDao.insertList(taskChainEntity.tasks))

            for (taskEntity in taskChainEntity.tasks) {
                insertCompletables.add(commentEntityDao.insertList(taskEntity.comments))
                for (commentEntity in taskEntity.comments) {
                    insertCompletables.add(saveNewUser(commentEntity.createdBy))
                }
            }
        }
        Completable.mergeArray(*insertCompletables.toTypedArray())
            .observeOn(schedulerProvider.ui())
            .subscribeOn(schedulerProvider.io())
            .subscribe({
                Log.d(TAG, "Data successfully saved in room")
            },{
                it.message?.let { it1 -> Log.d(TAG, it1) }
            }).addTo(compositeDisposable)
    }



    //TODO implement correct login and Handle errors
    override fun getUserWithUserName(userName: String): Single<UserEntity> {
        return userService.login(userName)
            .observeOn(schedulerProvider.ui())
            .subscribeOn(schedulerProvider.io())
            .map { userMapper.mapToEntity(it) }
            .doOnSuccess { user ->
                saveCurrentUser(user)
            }
    }

    private fun saveCurrentUser(currentUser: UserEntity) {
        currentUser.isCurrentUser = true
        Cache.currentUser = currentUser
        userEntityDao.insert(currentUser)
            .observeOn(schedulerProvider.ui())
            .subscribeOn(schedulerProvider.io())
            .subscribe().addTo(compositeDisposable)
    }

    //TODO Test
    private fun saveNewUser(userEntity: UserEntity): Completable {
        return userEntityDao.userExist(userEntity.id)
            .observeOn(schedulerProvider.ui())
            .subscribeOn(schedulerProvider.io())
            .filter{
                it == false
            }.flatMapCompletable {
                userEntityDao.insert(userEntity)
            }
    }

    override fun getTaskChain(taskChainId: Int): Single<TaskChainEntity> {

        return taskChainEntityDao.getById(taskChainId)
    }

    override fun getTask(taskId: Int): Single<TaskEntity> {
        return taskEntityDao.getById(taskId)
    }

    override fun getTasksForTaskChain(taskChainId: Int): Observable<List<TaskEntity>> {
        return taskEntityDao.getByTaskChainId(taskChainId)
    }

    override fun getCommentsForTask(taskId: Int): Observable<List<CommentEntity>> {
        return commentEntityDao.getByTaskId(taskId)
    }

    override fun getUser(userId: Int): Single<UserEntity> {
        return userEntityDao.getById(userId)
    }

    override fun getAllUsers(): Single<List<UserEntity>> {
        return userEntityDao.get()
    }

    override fun saveComment(commentEntity: CommentEntity): Completable {
        return commentEntityDao.insert(commentEntity)
    }

    override fun getCurrentUser(): Single<UserEntity> {
        return userEntityDao.getCurrentUser()
            .doOnSuccess{
                Cache.currentUser = it
            }
    }
}