package bettinger.david.taskchainapp.usecase.base


import bettinger.david.taskchainapp.usecase.scheduler.BaseSchedulerProvider
import io.reactivex.rxjava3.core.Single



/**
 * This abstract class is shared among several closely related UseCase classes
 * that classes that extend this abstract class to use common methods & fields
 **/
abstract class SingleUseCase<T>(private val schedulerProvider: BaseSchedulerProvider) : UseCase() {

    internal abstract fun buildUseCaseSingle(): Single<T>

    fun execute(
        onSuccess: ((t: T) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ) {
        disposeLast()
        lastDisposable = buildUseCaseSingle()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doAfterTerminate(onFinished)
            .subscribe(onSuccess, onError)

        lastDisposable?.let {
            compositeDisposable.add(it)
        }
    }
}