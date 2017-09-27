package babjo.org.taxidata.di

import android.content.Context
import babjo.org.taxidata.TaxiDataApp
import babjo.org.taxidata.presenter.TaxiPresenter
import babjo.org.taxidata.usecase.GetTaxiDataNearMeUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *
 * @author juancho.
 */
@Module
class AppModule(val app : TaxiDataApp) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideApplication(): TaxiDataApp {
        return app
    }

    @Provides
    @Singleton
    fun provideGetTaxiDataNearMeUseCase(): GetTaxiDataNearMeUseCase{
        return GetTaxiDataNearMeUseCase()
    }

    @Provides
    @Singleton
    fun provideTaxiPresenter(getTaxiDataNearMeUseCase: GetTaxiDataNearMeUseCase): TaxiPresenter{
        return TaxiPresenter(getTaxiDataNearMeUseCase)
    }
}