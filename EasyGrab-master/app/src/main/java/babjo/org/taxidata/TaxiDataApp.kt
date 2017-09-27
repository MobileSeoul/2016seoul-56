package babjo.org.taxidata

import android.app.Application
import babjo.org.taxidata.di.AppComponent
import babjo.org.taxidata.di.AppModule
import babjo.org.taxidata.di.DaggerAppComponent

/**
 * Created by LCH on 2016. 10. 27..
 */
class TaxiDataApp : Application(){

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}