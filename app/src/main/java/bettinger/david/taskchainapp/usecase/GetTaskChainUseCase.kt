package bettinger.david.taskchainapp.usecase

import bettinger.david.taskchainapp.data.entity.TaskChainEntity
import bettinger.david.taskchainapp.data.repository.TaskChainRepository
import bettinger.david.taskchainapp.usecase.base.SingleUseCase
import bettinger.david.taskchainapp.usecase.scheduler.BaseSchedulerProvider
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetTaskChainUseCase @Inject constructor(
    private val taskChainRepository: TaskChainRepository,
    schedulerProvider: BaseSchedulerProvider
) : SingleUseCase<TaskChainEntity>(schedulerProvider) {

    private var taskChainId: Int = -1

    fun setTaskChainId(id: Int) {
        taskChainId = id
    }

    override fun buildUseCaseSingle(): Single<TaskChainEntity> {
        return taskChainRepository.getTaskChain(taskChainId)
            .flatMap { taskChainEntity ->
                taskChainRepository.getUser(taskChainEntity.createdByUserId)
                    .map {
                        taskChainEntity.createdByUser = it
                        return@map taskChainEntity
                    }
            }
    }


}