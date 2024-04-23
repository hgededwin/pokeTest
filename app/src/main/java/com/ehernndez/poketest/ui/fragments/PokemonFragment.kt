package com.ehernndez.poketest.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ehernndez.poketest.R

class PokemonFragment : Fragment(R.layout.fragment_pokemon) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("[Thrity FRAGMENT] --->", "method onViewCreated was called")
    }
}