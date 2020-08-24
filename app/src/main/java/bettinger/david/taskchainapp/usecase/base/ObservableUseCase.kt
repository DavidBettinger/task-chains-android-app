package bettinger.david.taskchainapp.usecase.base

import bettinger.david.taskchainapp.usecase.scheduler.BaseSchedulerProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class ObservableUseCase<T>(private val schedulerProvider: BaseSchedulerProvider) : UseCase() {

    internal abstract fun buildUseCaseObservable(): Observable<T>

    fun execute(
        onNext: ((t: T) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ){
        disposeLast()
        lastDisposable = buildUseCaseObservable()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doAfterTerminate(onFinished)
            .subscribe(onNext, onError)

        lastDisposable?.let {
            compositeDisposable.add(it)
        }
    }

}