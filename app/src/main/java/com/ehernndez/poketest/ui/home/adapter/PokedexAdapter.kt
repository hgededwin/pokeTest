package com.ehernndez.poketest.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.api.models.Pokedex
import com.ehernndez.poketest.ui.home.PokemonDetailActivity

class PokedexAdapter(private val pokedexList: List<Pokedex>) :
    RecyclerView.Adapter<PokedexAdapter.ViewHolder>() {
        lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
       val view = LayoutInflater.from(parent.context).inflate(R.layout.pokedex_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = pokedexList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokedexList[position]
        holder.pokemonName.text = pokemon.name
        val txtUrl = "Fuente: ${pokemon.url}"
        holder.urlResources.text =  txtUrl

        val pokemonId = position + 1
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PokemonDetailActivity::class.java)
            intent.putExtra("POKEMON_ID", pokemonId)
            it.context.startActivity(intent)
        }
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val pokemonName: TextView = item.findViewById(R.id.txt_pokemon_name)
        val urlResources: TextView = item.findViewById(R.id.txt_pokemon_source_info)
    }

}