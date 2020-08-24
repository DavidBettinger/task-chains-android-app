package bettinger.david.taskchainapp.usecase

import bettinger.david.taskchainapp.data.entity.UserEntity
import bettinger.david.taskchainapp.data.repository.TaskChainRepository
import bettinger.david.taskchainapp.usecase.base.SingleUseCase
import bettinger.david.taskchainapp.usecase.scheduler.BaseSchedulerProvider
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val taskChainRepository: TaskChainRepository,
    schedulerProvider: BaseSchedulerProvider
) : SingleUseCase<UserEntity>(schedulerProvider) {


    override fun buildUseCaseSingle(): Single<UserEntity> {
        return taskChainRepository.getCurrentUser()
    }
}