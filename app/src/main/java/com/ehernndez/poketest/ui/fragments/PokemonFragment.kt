package com.ehernndez.poketest.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.lifecycle.lifecycleScope
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.persistantData.Data
import com.ehernndez.poketest.data.room.Pokemon
import com.ehernndez.poketest.data.room.PokemonDB
import com.ehernndez.poketest.ui.AddNewPokemonActivity
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.launch

class PokemonFragment : Fragment(R.layout.fragment_pokemon) {
    lateinit var room: PokemonDB
    lateinit var btnNewPokemon: Button
    lateinit var listViewPokemon: ListView
    var pokemonArrayList = arrayListOf<String>()

    var pokemonList = mutableListOf<Pokemon>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNewPokemon = view.findViewById(R.id.btn_new_pokemon)
        listViewPokemon = view.findViewById(R.id.lv_pokemon)

        room = Data.room

        btnNewPokemon.setOnClickListener {
            val intent = Intent(activity, AddNewPokemonActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getPokemonList(room, this.requireContext())
        Log.e("pokemon list -->", pokemonList.toString())
    }

    fun getPokemonList(room: PokemonDB, context: Context) {
        lifecycleScope.launch {
            pokemonList = room.daoPokemon().getPokemonList()
            pokemonArrayList = ArrayList(pokemonList.map { it.pokemonName })

            val adapter = ArrayAdapter(context, R.layout.dropdown_item, pokemonArrayList)
            listViewPokemon.adapter = adapter
            Log.e("---> PokemonList", pokemonList.toString())
        }
    }
}