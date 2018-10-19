package by.deniotokiari.gpslivetracking

import android.app.Application
import by.deniotokiari.gpslivetracking.di.appModule
import org.kodein.di.Kodein

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        kodein = Kodein {
            import(appModule(this@AppApplication))
        }
    }

    companion object {

        lateinit var kodein: Kodein

    }

}