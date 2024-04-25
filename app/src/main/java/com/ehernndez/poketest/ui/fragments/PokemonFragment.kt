package com.ehernndez.poketest.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.persistantData.Data
import com.ehernndez.poketest.data.room.Pokemon
import com.ehernndez.poketest.data.room.PokemonDB
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.launch

class PokemonFragment : Fragment(R.layout.fragment_pokemon) {
    lateinit var cardviewAddPokemon: MaterialCardView
    lateinit var room: PokemonDB
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        room = Data.room

        cardviewAddPokemon = view.findViewById(R.id.card_view_pokemon)

        cardviewAddPokemon.setOnClickListener {
            addNewPokemon(room, Pokemon(pokemonName = "Pikachu",pokemonType = "Fuego"))
        }

        Log.e("[Thrity FRAGMENT] --->", "method onViewCreated was called")
    }

    fun addNewPokemon(room: PokemonDB, pokemon: Pokemon) {
        lifecycleScope.launch {
            room.daoPokemon().setNewPokemon(pokemon)
        }
    }
}