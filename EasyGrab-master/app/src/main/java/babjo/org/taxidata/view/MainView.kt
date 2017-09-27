package babjo.org.taxidata.view

import babjo.org.taxidata.api.GetTaxiData

/**
 * Created by LCH on 2016. 10. 27..
 */

interface MainView {
    fun onPostSearchTaxiOn(resultList: Array<GetTaxiData>)
    fun onPreSearchTaxiOn()
    fun onErrorSearchTaxiOn(e: Throwable)
}
