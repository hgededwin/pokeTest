package com.ehernndez.poketest.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ehernndez.poketest.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.MaterialToolbar

class MapsPokemonActivity : AppCompatActivity(), OnMapReadyCallback {
    var googleMaps: GoogleMap? = null

    val firstLocation = LatLng(23.23567241556765, -106.4384338377423)
    val secondLication = LatLng(23.194969834634723, -106.42813415475247)
    val thirdLocation = LatLng(23.286769703937523, -106.47619934203831)

    var arrayLocations: ArrayList<LatLng>? = null

    lateinit var toolbar: MaterialToolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_pokemon)

        toolbar = findViewById(R.id.toolbar)
        val fragmentMap =
            supportFragmentManager.findFragmentById(R.id.fragment_maps) as SupportMapFragment
        fragmentMap.getMapAsync(this)

        arrayLocations = ArrayList()
        arrayLocations!!.add(firstLocation)
        arrayLocations!!.add(secondLication)
        arrayLocations!!.add(thirdLocation)

        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_terrain_map -> {
                    googleMaps!!.mapType = GoogleMap.MAP_TYPE_TERRAIN
                    googleMaps!!.isTrafficEnabled = false
                    true
                }

                R.id.item_normal_map -> {
                    googleMaps!!.mapType = GoogleMap.MAP_TYPE_NORMAL
                    googleMaps!!.isTrafficEnabled = true
                    true
                }

                R.id.item_satellite_map -> {
                    googleMaps!!.mapType = GoogleMap.MAP_TYPE_SATELLITE
                    googleMaps!!.isTrafficEnabled = false
                    true
                }

                else -> false
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMaps = map
        googleMaps!!.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMaps!!.uiSettings.isZoomControlsEnabled = true
        googleMaps!!.uiSettings.isCompassEnabled = true
        googleMaps!!.isIndoorEnabled = true
        googleMaps!!.uiSettings.isMyLocationButtonEnabled = true

        for (i in arrayLocations!!.indices) {
            googleMaps!!.addMarker(MarkerOptions().position(arrayLocations!![i]).title("Squirtle"))
            googleMaps!!.isTrafficEnabled = true
            googleMaps!!.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayLocations!![i], 10f))
        }
    }
}