package com.ehernndez.poketest.ui.home

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ehernndez.poketest.R

class PokemonDetailActivity : AppCompatActivity() {
    var pokemonId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        pokemonId = intent.getIntExtra("POKEMON_ID", 0)

        Log.e("the id pokemon is -->", pokemonId.toString())
    }
}