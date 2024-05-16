package com.ehernndez.poketest.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ehernndez.poketest.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapsPokemonActivity : AppCompatActivity(), OnMapReadyCallback {
    var googleMaps: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_pokemon)

        val fragmentMap =
            supportFragmentManager.findFragmentById(R.id.fragment_maps) as SupportMapFragment
        fragmentMap.getMapAsync(this)

    }

    override fun onMapReady(map: GoogleMap) {
        googleMaps = map
    }
}