package by.deniotokiari.gpslivetracking.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import by.deniotokiari.gpslivetracking.R
import by.deniotokiari.gpslivetracking.extension.kodein
import by.deniotokiari.gpslivetracking.livedata.LocationLiveData
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val locationTextView: TextView = findViewById(R.id.tv_location)

        val location: LocationLiveData by kodein.instance()

        location.observe(this, Observer { loc ->
            loc?.let {
                locationTextView.text = "lat: ${loc.latitude}, long: ${loc.longitude}"
            }
        })
    }

}
