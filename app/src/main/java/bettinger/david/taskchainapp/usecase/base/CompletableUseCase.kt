package bettinger.david.taskchainapp.usecase.base

import bettinger.david.taskchainapp.usecase.scheduler.BaseSchedulerProvider
import io.reactivex.rxjava3.core.Completable


abstract class CompletableUseCase(private val schedulerProvider: BaseSchedulerProvider): UseCase() {

    internal abstract fun buildUseCaseCompletable(): Completable

    fun execute(
        onSuccess: (() -> Unit),
        onError: ((t: Throwable) -> Unit),
    ) {
        disposeLast()
        lastDisposable = buildUseCaseCompletable()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(onSuccess, onError)

        lastDisposable?.let {
            compositeDisposable.add(it)
        }
    }

}