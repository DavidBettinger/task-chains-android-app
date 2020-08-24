package bettinger.david.taskchainapp.data.repository


import bettinger.david.taskchainapp.data.entity.CommentEntity
import bettinger.david.taskchainapp.data.entity.TaskChainEntity
import bettinger.david.taskchainapp.data.entity.TaskEntity
import bettinger.david.taskchainapp.data.entity.UserEntity
import io.reactivex.rxjava3.core.*

interface TaskChainRepository {

    fun getAllTaskChains(): Observable<List<TaskChainEntity>>

    fun loadDataFromApi()

    fun getUserWithUserName(userName: String): Single<UserEntity>

    fun getTaskChain(taskChainId: Int): Single<TaskChainEntity>

    fun getTask(taskId: Int): Single<TaskEntity>

    fun getTasksForTaskChain(taskChainId: Int): Observable<List<TaskEntity>>

    fun getCommentsForTask(taskId: Int): Observable<List<CommentEntity>>

    fun getUser(userId: Int): Single<UserEntity>

    fun getAllUsers(): Single<List<UserEntity>>

    fun saveComment(commentEntity: CommentEntity): Completable

    fun getCurrentUser(): Single<UserEntity>
}