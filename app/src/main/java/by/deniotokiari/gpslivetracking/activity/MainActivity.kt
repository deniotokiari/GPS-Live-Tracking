package by.deniotokiari.gpslivetracking.activity

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import by.deniotokiari.gpslivetracking.R
import by.deniotokiari.gpslivetracking.extension.kodein
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import org.kodein.di.generic.instance


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val location: LiveData<Location> by kodein.instance()

        GlobalScope.launch(Dispatchers.Main) {
            val resultA: Int = withContext(Dispatchers.IO) {
                Thread.sleep(10 * 1000)

                1
            }


            Toast.makeText(this@MainActivity, "Result: $resultA is done!", Toast.LENGTH_LONG).show()
        }

            /*val mapFragment: SupportMapFragment = SupportMapFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_map, mapFragment)
            .commit()

        mapFragment.getMapAsync { map: GoogleMap ->
            location.observe(this, Observer { loc ->
                loc?.let {
                    val currentLocation = LatLng(loc.latitude, loc.longitude)

                    map.addMarker(MarkerOptions().position(currentLocation).title("Current location"))
                    map.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
                }
            })
        }*/
    }

}
