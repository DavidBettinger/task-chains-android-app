package bettinger.david.taskchainapp.usecase

import bettinger.david.taskchainapp.data.entity.TaskEntity
import bettinger.david.taskchainapp.data.repository.TaskChainRepository
import bettinger.david.taskchainapp.usecase.base.ObservableUseCase
import bettinger.david.taskchainapp.usecase.scheduler.BaseSchedulerProvider
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskChainRepository: TaskChainRepository,
    schedulerProvider: BaseSchedulerProvider
) : ObservableUseCase<List<TaskEntity>>(schedulerProvider) {

    private var taskChainId: Int = -1

    fun setTaskChainId(id: Int) {
        taskChainId = id
    }

    override fun buildUseCaseObservable(): Observable<List<TaskEntity>> {
        return taskChainRepository.getTasksForTaskChain(taskChainId)
    }
}