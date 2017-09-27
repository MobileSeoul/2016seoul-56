package babjo.org.taxidata.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by LCH on 2016. 10. 25..
 */

interface TaxiApi {

    @POST("taxi/getTaxiDataNearMe")
    fun getTaxiDataNearMe(@Body request: GetTaxiDataNearMeRequest): Call<GetTaxiDataNearMeResponse>
}