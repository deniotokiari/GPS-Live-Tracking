package by.deniotokiari.gpslivetracking.fragment

import android.location.Location
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import by.deniotokiari.gpslivetracking.R
import by.deniotokiari.gpslivetracking.extension.kodein
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import org.kodein.di.generic.instance

class MapFragment : SupportMapFragment() {

    private val currentLocationTitle: String by kodein.instance(tag = "res", arg = R.string.YOUR_LOCATION)
    private val mapZoomLevel: Int by kodein.instance(tag = "res", arg = R.integer.MAP_ZOOM_VALUE)
    private val currentPositionMarker: BitmapDescriptor by lazy { BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_current) }
    private val otherPositionMarker: BitmapDescriptor by lazy { BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_other) }

    private val currentLocation: LiveData<Location> by kodein.instance()
    private val googleMap: MutableLiveData<GoogleMap> = MutableLiveData()

    private var currentMarker: Marker? = null
    private var zoomLevelInit: Boolean = false

    public var locationCallback: ((LatLng) -> Unit)? = null

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)

        /*googleMap.observe(this, Observer { map ->
            map?.let {
                currentLocation.observe(this, Observer { location ->
                    location?.let {
                        val latLng = LatLng(location.latitude, location.longitude)

                        currentMarker?.let { marker ->
                            if (distance(marker.position, latLng) >= 42) {
                                map.addMarker(
                                    MarkerOptions()
                                        .zIndex(-1F)
                                        .position(marker.position)
                                        .icon(otherPositionMarker)
                                )

                                locationCallback?.invoke(marker.position)
                            }

                            marker.remove()
                        }

                        currentMarker = map.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .zIndex(0F)
                                .title(currentLocationTitle)
                                .icon(currentPositionMarker)
                        )

                        if (zoomLevelInit) {
                            if (!map.projection.visibleRegion.latLngBounds.contains(latLng)) {
                                map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                            }
                        } else {
                            zoomLevelInit = true

                            map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                            map.animateCamera(CameraUpdateFactory.zoomBy(mapZoomLevel.toFloat()))
                        }
                    }
                })
            }
        })*/

        getMapAsync { map ->
            googleMap.value = map
        }
    }

    private fun distance(pos0: LatLng, pos1: LatLng): Float {
        val result = FloatArray(1)

        Location.distanceBetween(pos0.latitude, pos0.longitude, pos1.latitude, pos1.longitude, result)

        return result[0]
    }

}