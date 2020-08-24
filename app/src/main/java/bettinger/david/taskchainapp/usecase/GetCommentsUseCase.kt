package bettinger.david.taskchainapp.usecase

import bettinger.david.taskchainapp.data.entity.CommentEntity
import bettinger.david.taskchainapp.data.repository.TaskChainRepository
import bettinger.david.taskchainapp.usecase.base.ObservableUseCase
import bettinger.david.taskchainapp.usecase.scheduler.BaseSchedulerProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val taskChainRepository: TaskChainRepository,
    schedulerProvider: BaseSchedulerProvider
) : ObservableUseCase<List<CommentEntity>>(schedulerProvider) {

    private var taskId: Int = -1

    fun setTaskId(id: Int) {
        taskId = id
    }

    override fun buildUseCaseObservable(): Observable<List<CommentEntity>> {
        return taskChainRepository.getCommentsForTask(taskId)
            .flatMap { comments ->
                mapUsers(comments).toObservable()
            }

    }

    private fun mapUsers(comments: List<CommentEntity>): Single<List<CommentEntity>> {
        val singles = mutableListOf<Single<CommentEntity>>()
        comments.forEach {comment ->
            singles.add(
            taskChainRepository.getUser(comment.createdByUserId)
                .map { user ->
                    comment.createdBy = user
                    return@map comment
                }
            )
        }
        return Single.mergeArray(*singles.toTypedArray()).toList()
    }
}