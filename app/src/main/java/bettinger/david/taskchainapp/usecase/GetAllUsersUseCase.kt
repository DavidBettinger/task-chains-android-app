package bettinger.david.taskchainapp.usecase

import bettinger.david.taskchainapp.data.entity.UserEntity
import bettinger.david.taskchainapp.data.repository.TaskChainRepository
import bettinger.david.taskchainapp.usecase.base.SingleUseCase
import bettinger.david.taskchainapp.usecase.scheduler.BaseSchedulerProvider
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val taskChainRepository: TaskChainRepository,
    schedulerProvider: BaseSchedulerProvider
) : SingleUseCase<List<UserEntity>>(schedulerProvider) {

    override fun buildUseCaseSingle(): Single<List<UserEntity>> {
        return taskChainRepository.getAllUsers()
    }
}