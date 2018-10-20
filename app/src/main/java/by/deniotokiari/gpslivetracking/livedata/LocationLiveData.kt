package by.deniotokiari.gpslivetracking.livedata

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.content.Context
import android.location.Location
import android.text.format.DateUtils
import com.google.android.gms.location.*

class LocationLiveData(private val context: Context) : LiveData<Location>() {

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(
                context
        )
    }
    private val locationCallback: LocationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.locations?.forEach { location ->
                    value = location
                }
            }
        }
    }

    private val locationRequest: LocationRequest by lazy {
        LocationRequest().apply {
            fastestInterval = DateUtils.SECOND_IN_MILLIS * 1
            interval = DateUtils.SECOND_IN_MILLIS * 2
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun onInactive() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

}