package babjo.org.taxidata.api

/**
 * Created by LCH on 2016. 10. 25..
 */

data class GetTaxiDataNearMeRequest(val longitude : Double, val latitude : Double, val distance : Double, val day: Int? = null, val time: Int? = null)
class GetTaxiDataNearMeResponse(val data: GetTaxiDataNearMeResponseData)
class GetTaxiDataNearMeResponseData(val resultList : Array<GetTaxiData>)
class GetTaxiData(val points : Array<Point>, val cntOn : Int, val cntOff : Int)
data class Point(val x : Double, val y : Double)