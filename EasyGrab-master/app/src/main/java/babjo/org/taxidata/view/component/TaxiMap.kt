package babjo.org.taxidata.view.component
import android.graphics.Color
import android.util.Log
import babjo.org.taxidata.Const
import babjo.org.taxidata.api.Point
import babjo.org.taxidata.view.DefaultOpenAPIKeyAuthenticationResultListener
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPolyline
import net.daum.mf.map.api.MapView
/**
 * Created by LCH on 2016. 10. 27..
 */
class TaxiMap : MapView.MapViewEventListener, MapView.CurrentLocationEventListener{

    private val TAG = TaxiMap::class.simpleName
    private val mMapView : MapView
    private var isTracking: Boolean = false
    var onCurrentLocationUpdate : () -> Unit = {}

    constructor(mapView: MapView){
        mMapView = mapView
        mapView.setDaumMapApiKey(Const.DAUM_MAPS_ANDROID_APP_API_KEY)
        mapView.setOpenAPIKeyAuthenticationResultListener(DefaultOpenAPIKeyAuthenticationResultListener())
        mapView.setMapViewEventListener(this)
        mapView.setCurrentLocationEventListener(this)
        mapView.mapType = MapView.MapType.Standard
    }

    fun switchTracking() : Boolean{
        if (isTracking) {
            trackingModeOff()
            isTracking = false
        } else {
            trackingModeOn()
            isTracking = true
        }
        return isTracking
    }

    fun trackingModeOff() {
        mMapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOff
    }

    fun trackingModeOn() {
        mMapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading
    }

    fun getMapCenterPoint(): MapPoint = mMapView.mapCenterPoint!!

    fun removeAllPolylines() {
        mMapView.removeAllPolylines()
    }

    fun removeAllAndAddCircle(mapPoint: MapPoint?) {
        mMapView.removeAllCircles()
        mMapView.addCircle(MapCircle(
                mapPoint, // center
                10, // radius
                Color.argb(128, 0, 255, 0), // strokeColor
                Color.argb(128, 0, 255, 0) // fillColor
        ))
    }

    fun addPolyline(points : Array<Point>, num:Int) {
        mMapView.addPolyline(createPolyline(points, num))
    }

    private fun createPolyline(points : Array<Point>, num: Int) : MapPolyline {
        val polyline = MapPolyline()
        when(num){
            in 0..12 -> polyline.lineColor = Color.rgb(num * 20, 255, 0)
            in 13..24 -> polyline.lineColor = Color.rgb(255, 255 - (num - 20) * 15, 0)
        }
        points.forEach { polyline.addPoint(MapPoint.mapPointWithGeoCoord(it.y, it.x)) }
        return polyline
    }

    override fun onMapViewInitialized(mapView: MapView?) {
        Log.i(TAG, "MapView had loaded. Now, MapView APIs could be called safely")
        removeAllAndAddCircle(mapView?.mapCenterPoint)
    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewCenterPointMoved(mapView: MapView?, mapPoint: MapPoint?) {
        removeAllAndAddCircle(mapPoint)
    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
    }

    override fun onCurrentLocationUpdateFailed(p0: MapView?) {
    }

    override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {
    }

    override fun onCurrentLocationUpdate(mapView: MapView?, currentLocation: MapPoint?, accuracyInMeters: Float) {
        val mapPointGeo = currentLocation!!.mapPointGeoCoord
        Log.i(TAG, "MapView onCurrentLocationUpdate (${mapPointGeo.latitude},${mapPointGeo.longitude}) accuracy ($accuracyInMeters)")
        removeAllAndAddCircle(currentLocation)
        onCurrentLocationUpdate()
    }

    override fun onCurrentLocationUpdateCancelled(p0: MapView?) {
    }
}