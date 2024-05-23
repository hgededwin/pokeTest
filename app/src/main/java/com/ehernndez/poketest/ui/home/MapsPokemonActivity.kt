package com.ehernndez.poketest.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ehernndez.poketest.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MapsPokemonActivity : AppCompatActivity(), OnMapReadyCallback {
    var googleMaps: GoogleMap? = null

    val firstLocation = LatLng(23.23567241556765, -106.4384338377423)
    val secondLication = LatLng(23.194969834634723, -106.42813415475247)
    val thirdLocation = LatLng(23.286769703937523, -106.47619934203831)
    var arrayLocations: ArrayList<LatLng>? = null

    lateinit var currentLocation: Location
    lateinit var flpc: FusedLocationProviderClient

    lateinit var toolbar: MaterialToolbar
    lateinit var btnPermissions: ExtendedFloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_pokemon)

        toolbar = findViewById(R.id.toolbar)
        btnPermissions = findViewById(R.id.btn_permissions)
        btnPermissions.visibility = View.GONE

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

        flpc = LocationServices.getFusedLocationProviderClient(this)

        getLasLocation()

        btnPermissions.setOnClickListener {
            getLasLocation()
        }
    }

    private fun getLasLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            return
        }
        val task: Task<Location> = flpc.lastLocation
        task.addOnSuccessListener { location ->
            currentLocation = location

            val fragmentMap =
                supportFragmentManager.findFragmentById(R.id.fragment_maps) as SupportMapFragment
            fragmentMap.getMapAsync(this)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLasLocation()
                btnPermissions.visibility = View.GONE
            } else {
                btnPermissions.visibility = View.VISIBLE
                Toast.makeText(this, "Es necesario aceptar los permisos para continuar.", Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onMapReady(map: GoogleMap) {
        googleMaps = map
        googleMaps!!.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMaps!!.uiSettings.isZoomControlsEnabled = true
        googleMaps!!.uiSettings.isCompassEnabled = true
        googleMaps!!.uiSettings.isRotateGesturesEnabled = true
        googleMaps!!.uiSettings.isIndoorLevelPickerEnabled = true
        googleMaps!!.uiSettings.isMyLocationButtonEnabled = true


        val location = LatLng(currentLocation.latitude, currentLocation.longitude)
        googleMaps!!.addMarker(MarkerOptions().position(location).title("Mi ubicación actual"))
        googleMaps!!.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 20f))

        for (i in arrayLocations!!.indices) {
            googleMaps!!.addMarker(MarkerOptions().position(arrayLocations!![i]).title("Squirtle"))
            googleMaps!!.isTrafficEnabled = true
           //googleMaps!!.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayLocations!![i], 10f))
        }
    }
}