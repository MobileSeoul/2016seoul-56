package babjo.org.taxidata

import babjo.org.taxidata.api.GetTaxiDataNearMeRequest
import babjo.org.taxidata.api.GetTaxiDataNearMeResponse
import babjo.org.taxidata.api.TaxiApi
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.Subscriber
import rx.internal.util.BlockingUtils
import java.util.concurrent.CountDownLatch


/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        val subscription = get().subscribe(object : Subscriber<Response<GetTaxiDataNearMeResponse>>() {
            override fun onNext(t: Response<GetTaxiDataNearMeResponse>?) {
                val getTaxiDataNearMeResponse = t?.body()

                getTaxiDataNearMeResponse?.data?.resultList?.forEach {
                    println(it.points.size)
                    println(it.cntOn)
                    println(it.cntOff)
                }
            }

            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {
                println(e)
            }
        })

        BlockingUtils.awaitForComplete(CountDownLatch(1), subscription)
    }

    fun get(): Observable<Response<GetTaxiDataNearMeResponse>> {
        return Observable.create {
            subscriber ->
            val baseUrl = "http://52.78.187.179:8080/taxi-data/"
            val client = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
            val service = client.create(TaxiApi::class.java)
            val call = service.getTaxiDataNearMe(GetTaxiDataNearMeRequest(126.991990, 37.570253, 0.1))
            call.enqueue(object : Callback<GetTaxiDataNearMeResponse> {
                override fun onFailure(call: Call<GetTaxiDataNearMeResponse>?, t: Throwable?) {
                    subscriber.onError(RuntimeException())
                }
                override fun onResponse(call: Call<GetTaxiDataNearMeResponse>?, response: Response<GetTaxiDataNearMeResponse>?) {
                    subscriber.onNext(response)
                    subscriber.onCompleted()
                }
            })
        }
    }
}