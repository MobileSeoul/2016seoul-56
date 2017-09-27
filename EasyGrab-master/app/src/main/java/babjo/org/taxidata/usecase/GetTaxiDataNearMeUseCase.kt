package babjo.org.taxidata.usecase

import babjo.org.taxidata.api.GetTaxiDataNearMeRequest
import babjo.org.taxidata.api.GetTaxiDataNearMeResponse
import babjo.org.taxidata.api.TaxiApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

/**
 * Created by LCH on 2016. 10. 27..
 */
class GetTaxiDataNearMeUseCase : UseCase<GetTaxiDataNearMeRequest, GetTaxiDataNearMeResponse>() {
    override fun buildUseCaseObservable(t: GetTaxiDataNearMeRequest): Observable<GetTaxiDataNearMeResponse> {
        val baseUrl = "http://52.78.187.179:8080/taxi-data/"
        val client = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        val service = client.create(TaxiApi::class.java)
        return Observable.create {
            subscriber ->
            val call = service.getTaxiDataNearMe(t)
            call.enqueue(object : Callback<GetTaxiDataNearMeResponse> {
                override fun onFailure(call: Call<GetTaxiDataNearMeResponse>?, t: Throwable?) {
                    subscriber.onError(RuntimeException())
                }
                override fun onResponse(call: Call<GetTaxiDataNearMeResponse>?, response: Response<GetTaxiDataNearMeResponse>?) {
                    subscriber.onNext(response?.body())
                    subscriber.onCompleted()
                }
            })
        }
    }
}


