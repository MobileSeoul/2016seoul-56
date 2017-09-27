package babjo.org.taxidata.view

import android.util.Log
import net.daum.mf.map.api.MapView

/**
 * Created by LCH on 2016. 10. 27..
 */

class DefaultOpenAPIKeyAuthenticationResultListener : MapView.OpenAPIKeyAuthenticationResultListener{
    private val TAG : String = DefaultOpenAPIKeyAuthenticationResultListener::class.java.simpleName
    override fun onDaumMapOpenAPIKeyAuthenticationResult(mapView: MapView?, resultCode : Int, resultMessage: String?) {
        Log.i(TAG, "Open API Key Authentication Result : code=$resultCode, message=$resultMessage")
    }
}
