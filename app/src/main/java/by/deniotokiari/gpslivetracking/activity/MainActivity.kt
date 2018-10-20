package by.deniotokiari.gpslivetracking.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import by.deniotokiari.gpslivetracking.R
import by.deniotokiari.gpslivetracking.fragment.MapFragment
import com.google.android.gms.maps.model.LatLng


class MainActivity : AppCompatActivity() {

    val MAP: Int = 0
    val LOCATION: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val collectionRv: RecyclerView = findViewById(R.id.rv_collection)
        collectionRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        collectionRv.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            var locations: ArrayList<LatLng> = ArrayList()

            override fun onCreateViewHolder(root: ViewGroup, type: Int): RecyclerView.ViewHolder {
                if (type == MAP) {
                    return object : RecyclerView.ViewHolder(
                        LayoutInflater.from(this@MainActivity).inflate(
                            R.layout.adapter_map,
                            root,
                            false
                        )
                    ) {}
                } else {
                    return object : RecyclerView.ViewHolder(
                        LayoutInflater.from(this@MainActivity).inflate(
                            R.layout.adapter_location,
                            root,
                            false
                        )
                    ) {}
                }
            }

            override fun getItemViewType(position: Int): Int {
                return when (position) {
                    0 -> MAP
                    else -> LOCATION
                }
            }

            override fun getItemCount(): Int = 1 + locations.size

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                when (getItemViewType(position)) {
                    MAP -> {
                        val map: MapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment

                        map.locationCallback = { pos ->
                            locations.add(pos)

                            notifyItemInserted(locations.size)
                        }
                    }
                    LOCATION -> {
                        val index = position - 1
                        val position: LatLng = locations[index]

                        holder.itemView.findViewById<TextView>(R.id.location_title).setText("Lat: ${position.latitude}, lng: ${position.longitude}")
                    }
                }
            }

        }
    }

}

/*
GlobalScope.launch(Dispatchers.Main) {
            val resultA: Int = withContext(Dispatchers.IO) {
                Thread.sleep(10 * 1000)

                1
            }


            Toast.makeText(this@MainActivity, "Result: $resultA is done!", Toast.LENGTH_LONG).show()
        }
 */
