package by.deniotokiari.gpslivetracking.activity

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import by.deniotokiari.gpslivetracking.LocationService
import by.deniotokiari.gpslivetracking.extension.kodein
import org.kodein.di.generic.instance

class AddLocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startService(Intent(this, LocationService::class.java))

        val locationLiveData: LiveData<Location> by kodein.instance()

        locationLiveData.observe(this, Observer {
            Log.d("LOG", "Location: ${it.latitude}, ${it.longitude}")
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        stopService(Intent(this, LocationService::class.java))
    }

}