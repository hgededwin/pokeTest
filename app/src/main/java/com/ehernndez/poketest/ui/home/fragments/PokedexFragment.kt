package com.ehernndez.poketest.ui.home.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.api.models.PokedexViewModel

class PokedexFragment : Fragment(R.layout.fragment_pokedex) {
    lateinit var viewModel: PokedexViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[PokedexViewModel::class.java]

        viewModel.getPodexInfo() // in this moment the viewmodel has called the service pokeapi/pokemon
        viewModel.pokemonList.observe(viewLifecycleOwner) { list ->
            Log.e("pokemon list from our service --->", list.size.toString())
        }
    }
}