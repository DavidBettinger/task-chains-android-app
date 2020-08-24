package bettinger.david.taskchainapp.usecase

import bettinger.david.taskchainapp.data.repository.TaskChainRepository
import bettinger.david.taskchainapp.usecase.base.CompletableUseCase
import bettinger.david.taskchainapp.usecase.scheduler.BaseSchedulerProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.CompletableSubject
import javax.inject.Inject

class RefreshCacheUseCase @Inject constructor(private val taskChainRepository: TaskChainRepository) {


    fun execute(){
        taskChainRepository.loadDataFromApi()
    }

}