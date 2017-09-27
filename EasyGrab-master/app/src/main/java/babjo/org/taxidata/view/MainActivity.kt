package babjo.org.taxidata.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import babjo.org.taxidata.Const
import babjo.org.taxidata.R
import babjo.org.taxidata.TaxiDataApp
import babjo.org.taxidata.api.GetTaxiData
import babjo.org.taxidata.presenter.TaxiPresenter
import babjo.org.taxidata.view.component.TaxiMap
import kotlinx.android.synthetic.main.activity_main.*
import net.daum.mf.map.api.MapLayout
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    private val TAG = MainActivity::class.simpleName
    private lateinit var mTaxiMap: TaxiMap

    @Inject
    lateinit var mTaxiPresenter: TaxiPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBarTranslucent(true)
        TaxiDataApp.appComponent.inject(this)

        val mapLayout = MapLayout(this)

        mTaxiMap = TaxiMap(mapLayout.mapView)
        map_view.addView(mapLayout)

        mTaxiMap.onCurrentLocationUpdate = { progress.visibility = View.INVISIBLE }

        fab.setOnClickListener {
            checkGPSPermission(this, Const.PERMISSION_REQUEST_TRACKING, {
                switchTracking()
            })
        }
        searchTaxiNearMe.setOnClickListener {
            checkGPSPermission(this, Const.PERMISSION_REQUEST_SEARCHING, {mTaxiPresenter.searchTaxiNearMe(mTaxiMap.getMapCenterPoint().mapPointGeoCoord.longitude, mTaxiMap.getMapCenterPoint().mapPointGeoCoord.latitude)})
        }
        mTaxiPresenter.mMainView = this
    }

    private fun switchTracking() {
        if (mTaxiMap.switchTracking()) {
            fab.setImageDrawable(resources.getDrawable(R.drawable.ic_gps_fixed_white_36dp))
            fab.backgroundTintList = ColorStateList.valueOf(Color.parseColor(resources.getString(R.color.colorAccent)))
            progress.visibility = View.VISIBLE
        } else {
            fab.setImageDrawable(resources.getDrawable(R.drawable.ic_gps_fixed_black_36dp))
            fab.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            progress.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTaxiMap.trackingModeOff()
        mTaxiPresenter.destroy()
    }

    fun checkGPSPermission(activity: Activity, requestCode: Int, callback: () -> Unit) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), requestCode)
            else
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), requestCode)
        } else {
            callback()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            Const.PERMISSION_REQUEST_TRACKING -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    switchTracking()
                } else {
                    Toast.makeText(this, Const.PERMISSION_GSP_FAIL_MSG, Toast.LENGTH_LONG).show();
                }
                return
            }
            Const.PERMISSION_REQUEST_SEARCHING ->{
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mTaxiPresenter.searchTaxiNearMe(mTaxiMap.getMapCenterPoint().mapPointGeoCoord.longitude, mTaxiMap.getMapCenterPoint().mapPointGeoCoord.latitude)
                } else {
                    Toast.makeText(this, Const.PERMISSION_GSP_FAIL_MSG, Toast.LENGTH_LONG).show();
                }
                return
            }
        }
    }

    override fun onPreSearchTaxiOn() {
        mTaxiMap.removeAllPolylines()
    }

    override fun onPostSearchTaxiOn(resultList: Array<GetTaxiData>) {
        if(resultList.size == 0)
            Toast.makeText(this, Const.USE_CASE_SEARCH_TAXI_NO_RESULT, Toast.LENGTH_LONG).show()
        else {
            resultList.sortByDescending { it.cntOn + it.cntOff }
            Log.d(TAG, "Searched TaxiData is a total ${resultList.size}")
            resultList.take(24).forEachIndexed { i, taxiData ->
                mTaxiMap.addPolyline(taxiData.points, i)
            }
        }

    }

    override fun onErrorSearchTaxiOn(e: Throwable) {
        Toast.makeText(this, Const.USE_CASE_SEARCH_TAXI_EXCEPTION_MSG, Toast.LENGTH_LONG).show()
    }

    protected fun setStatusBarTranslucent(makeTranslucent: Boolean) {
        if (makeTranslucent) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }
}
