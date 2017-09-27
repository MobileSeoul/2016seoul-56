package babjo.org.taxidata.usecase

import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.Subscriptions

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).

 * By convention each UseCase implementation will return the result using a [rx.Subscriber]
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
abstract class UseCase<T, K> {

    private var subscription = Subscriptions.empty()

    /**
     * Builds an [rx.Observable] which will be used when executing the current [UseCase].
     */
    protected abstract fun buildUseCaseObservable(t: T): Observable<K>

    /**
     * Executes the current use case.

     * @param useCaseSubscriber The guy who will be listen to the observable build
     * * with [.buildUseCaseObservable].
     */
    @SuppressWarnings("unchecked")
    fun execute(t: T, useCaseSubscriber: Subscriber<K>) {
        this.subscription = this.buildUseCaseObservable(t).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(useCaseSubscriber)
    }

    /**
     * Unsubscribes from current [rx.Subscription].
     */
    fun unsubscribe() {
        if (!subscription.isUnsubscribed) {
            subscription.unsubscribe()
        }
    }
}