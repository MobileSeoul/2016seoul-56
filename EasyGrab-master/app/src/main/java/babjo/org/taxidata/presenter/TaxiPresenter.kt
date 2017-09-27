package babjo.org.taxidata.presenter

import android.util.Log
import babjo.org.taxidata.api.GetTaxiDataNearMeRequest
import babjo.org.taxidata.api.GetTaxiDataNearMeResponse
import babjo.org.taxidata.usecase.DefaultSubscriber
import babjo.org.taxidata.usecase.GetTaxiDataNearMeUseCase
import babjo.org.taxidata.view.MainView

/**
 * Created by LCH on 2016. 10. 27..
 */

class TaxiPresenter(val getTaxiDataNearMeUseCase: GetTaxiDataNearMeUseCase) : Presenter{

    private val TAG = TaxiPresenter::class.simpleName
    lateinit var mMainView : MainView

    override fun pause() {
    }

    override fun destroy() {
        getTaxiDataNearMeUseCase.unsubscribe()
    }

    override fun resume() {
    }

    fun searchTaxiNearMe(longitude: Double, latitude: Double) {
        mMainView.onPreSearchTaxiOn()
        getTaxiDataNearMeUseCase.execute(GetTaxiDataNearMeRequest(longitude, latitude, 0.2),
                object : DefaultSubscriber<GetTaxiDataNearMeResponse>() {
                    override fun onNext(o: GetTaxiDataNearMeResponse) {
                        Log.d(TAG, ""+o.data.resultList.size)
                        mMainView.onPostSearchTaxiOn(o.data.resultList)
                    }
                    override fun onError(e: Throwable) {
                        mMainView.onErrorSearchTaxiOn(e)
                    }
            })
    }
}
