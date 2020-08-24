package bettinger.david.taskchainapp.usecase

import bettinger.david.taskchainapp.data.entity.TaskChainEntity
import bettinger.david.taskchainapp.data.repository.TaskChainRepository
import bettinger.david.taskchainapp.usecase.base.ObservableUseCase
import bettinger.david.taskchainapp.usecase.scheduler.BaseSchedulerProvider
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetTaskChainsUseCase @Inject constructor(
    private val taskChainRepository: TaskChainRepository,
    schedulerProvider: BaseSchedulerProvider
) : ObservableUseCase<List<TaskChainEntity>>(schedulerProvider) {


    override fun buildUseCaseObservable(): Observable<List<TaskChainEntity>> {
        return taskChainRepository.getAllTaskChains()
    }


}