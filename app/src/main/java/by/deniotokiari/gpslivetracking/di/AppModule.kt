package by.deniotokiari.gpslivetracking.di

import android.arch.lifecycle.LiveData
import android.content.Context
import android.location.Location
import by.deniotokiari.gpslivetracking.livedata.LocationLiveData
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

// holds app visible singleton instances
fun appModule(context: Context): Kodein.Module = Kodein.Module("appModule") {

    bind<Context>() with singleton { context }

    bind<LiveData<Location>>() with singleton { LocationLiveData(context) }

}
