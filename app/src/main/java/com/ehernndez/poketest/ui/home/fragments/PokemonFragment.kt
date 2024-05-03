package com.ehernndez.poketest.ui.home.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.persistantData.Data
import com.ehernndez.poketest.data.room.Pokemon
import com.ehernndez.poketest.data.room.PokemonDB
import com.ehernndez.poketest.ui.pokemon.AddNewPokemonActivity
import com.ehernndez.poketest.utils.Utils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class PokemonFragment : Fragment(R.layout.fragment_pokemon) {
    lateinit var room: PokemonDB
    lateinit var btnNewPokemon: Button
    lateinit var listViewPokemon: ListView
    lateinit var adapter: ArrayAdapter<String>

    var pokemonArrayList = arrayListOf<String>()
    var pokemonList = mutableListOf<Pokemon>()
    var isUndo = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNewPokemon = view.findViewById(R.id.btn_new_pokemon)
        listViewPokemon = view.findViewById(R.id.lv_pokemon)

        room = Data.room

        btnNewPokemon.setOnClickListener {
            val intent = Intent(activity, AddNewPokemonActivity::class.java)
            startActivity(intent)
        }

        deletePokemon()
    }

    override fun onResume() {
        super.onResume()
        getPokemonList(this.requireContext())
        Log.e("pokemon list -->", pokemonList.toString())
    }

    fun getPokemonList(context: Context) {
        lifecycleScope.launch {
            pokemonList = room.daoPokemon().getPokemonList()
            pokemonArrayList = ArrayList(pokemonList.map { it.pokemonName })

            adapter = ArrayAdapter(context, R.layout.dropdown_item, pokemonArrayList)
            listViewPokemon.adapter = adapter
            Log.e("---> PokemonList", pokemonList.toString())
            Log.e("---> PokemonArrayList", pokemonArrayList.toString())
        }
    }

    fun deletePokemon() {
        listViewPokemon.setOnItemLongClickListener { parent, view, position, id ->
            val currentPokemon = listViewPokemon.getItemAtPosition(position)
            MaterialAlertDialogBuilder(this.requireContext())
                .setTitle("PokeTest")
                .setMessage("Has seleccionado a $currentPokemon. ¿Qué deseas hacer con él?")
                .setPositiveButton("Eliminar") { dialog, which ->
                    pokemonArrayList.removeAt(position)
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()

                    showSnackbar(view, currentPokemon.toString())
                }
                .setNeutralButton("Actualizar") { dialog, which ->
                    val intent = Intent(requireContext(), AddNewPokemonActivity::class.java)
                    intent.putExtra("UPDATE_FLOW", true)
                    startActivity(intent)
                }
                .setNegativeButton("Cancelar") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
            false
        }
    }

    fun showSnackbar(view: View, pokemon: String) {
        Snackbar.make(view, "Has eliminado el pokemon $pokemon", Snackbar.LENGTH_LONG)
            .setAction("Deshacer") {
                getPokemonList(this.requireContext())
                isUndo = true
            }
            .addCallback(object : BaseCallback<Snackbar>() {

                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    if (!isUndo) {
                        removePokemonFromDB(pokemon)
                        isUndo = false
                    }
                }
            })
            .show()
    }

    fun removePokemonFromDB(pokemon: String) {
        lifecycleScope.launch {
            room.daoPokemon().deletePokemonInfo(pokemon)
            getPokemonList(requireContext())
        }
    }
}