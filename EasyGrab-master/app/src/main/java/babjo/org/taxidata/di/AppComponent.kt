package babjo.org.taxidata.di
import babjo.org.taxidata.view.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 *
 * @author juancho.
 */
@Singleton
@Component(modules =
    arrayOf(AppModule::class)
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}