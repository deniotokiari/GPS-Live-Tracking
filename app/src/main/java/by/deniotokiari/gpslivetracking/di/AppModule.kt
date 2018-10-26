package by.deniotokiari.gpslivetracking.di

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import by.deniotokiari.gpslivetracking.livedata.LocationLiveData
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.singleton

fun appModule(context: Context): Kodein.Module = Kodein.Module("appModule") {

    bind<Context>() with singleton { context }

    bind<LiveData<Location>>() with singleton { LocationLiveData(context) }

    bind<String>(tag = "res") with factory { res: Int -> context.getString(res) }

    bind<Int>(tag = "res") with factory { res: Int -> context.resources.getInteger(res) }

}
