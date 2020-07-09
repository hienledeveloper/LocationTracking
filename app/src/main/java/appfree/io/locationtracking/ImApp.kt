package appfree.io.locationtracking

import android.app.Application
import appfree.io.locationtracking.di.managerInjection
import appfree.io.locationtracking.di.viewModelInjection
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created By Ben on 7/9/20
 */
class ImApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                managerInjection,
                viewModelInjection
            )
        }
    }

}