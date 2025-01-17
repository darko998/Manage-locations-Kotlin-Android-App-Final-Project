package rs.raf.projekat2.darko_dimitrijevic_rn9418.application

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import rs.raf.projekat2.darko_dimitrijevic_rn9418.modules.coreModule
import rs.raf.projekat2.darko_dimitrijevic_rn9418.modules.locationModule
import timber.log.Timber

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    fun init() {
        initTimber()
        initKoin()
        initStetho()
    }

    fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    fun initKoin() {
        val modules = listOf(
            coreModule,
            locationModule
        )

        startKoin {
            androidLogger(Level.DEBUG)
            // Use application context
            androidContext(this@Application)
            // Use properties from assets/koin.properties
            androidFileProperties()
            // Use koin fragment factory for fragment instantiation
            fragmentFactory()
            // modules
            modules(modules)
        }
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }
}