package bettinger.david.taskchainapp.usecase

import bettinger.david.taskchainapp.data.entity.TaskEntity
import bettinger.david.taskchainapp.data.repository.TaskChainRepository
import bettinger.david.taskchainapp.usecase.base.SingleUseCase
import bettinger.david.taskchainapp.usecase.scheduler.BaseSchedulerProvider
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val taskChainRepository: TaskChainRepository,
    schedulerProvider: BaseSchedulerProvider
) : SingleUseCase<TaskEntity>(schedulerProvider) {

    private var taskId: Int = -1

    fun setTaskId(id: Int) {
        taskId = id
    }

    override fun buildUseCaseSingle(): Single<TaskEntity> {
        return taskChainRepository.getTask(taskId)
    }


}