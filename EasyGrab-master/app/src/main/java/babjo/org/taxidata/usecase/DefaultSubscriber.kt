package babjo.org.taxidata.usecase

import rx.Subscriber

/**
 * Created by LCH on 2016. 9. 22..
 */

open class DefaultSubscriber<T> : Subscriber<T>() {
    override fun onCompleted() {
    }
    override fun onError(e: Throwable) {
    }
    override fun onNext(o: T) {
    }
}


