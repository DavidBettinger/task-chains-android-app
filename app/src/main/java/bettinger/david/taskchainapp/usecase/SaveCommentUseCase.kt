package bettinger.david.taskchainapp.usecase

import bettinger.david.taskchainapp.data.entity.CommentEntity
import bettinger.david.taskchainapp.data.repository.TaskChainRepository
import bettinger.david.taskchainapp.usecase.base.CompletableUseCase
import bettinger.david.taskchainapp.usecase.scheduler.BaseSchedulerProvider
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class SaveCommentUseCase @Inject constructor(
    private val taskChainRepository: TaskChainRepository,
    schedulerProvider: BaseSchedulerProvider
) : CompletableUseCase(schedulerProvider) {

    private var commentToSave: CommentEntity? = null

    fun saveComment(commentEntity: CommentEntity) {
        commentToSave = commentEntity
    }

    override fun buildUseCaseCompletable(): Completable {
        return taskChainRepository.saveComment(commentToSave!!)
    }


}