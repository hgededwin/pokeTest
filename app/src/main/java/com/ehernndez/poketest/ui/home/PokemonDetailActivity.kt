package com.ehernndez.poketest.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.api.models.PokedexViewModel
import com.ehernndez.poketest.utils.capitalizeFirstLetterOfWord
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.materialswitch.MaterialSwitch
import com.squareup.picasso.Picasso

class PokemonDetailActivity : AppCompatActivity() {
    var pokemonId = 0
    var isRotated = false
    var isShiny = false
    lateinit var frontSprite: String
    lateinit var backSprite: String
    lateinit var frontShinySprite: String
    lateinit var backShinySprite: String
    lateinit var viewModel: PokedexViewModel
    lateinit var toolbar: MaterialToolbar
    lateinit var imgPokemonSprite: ImageView
    lateinit var txtPokemonDescription: TextView
    lateinit var imgFirstPokemonType: ImageView
    lateinit var imgSecondPokemonType: ImageView
    lateinit var txtHeight: TextView
    lateinit var txtWeight: TextView
    lateinit var btnRotateSprite: FloatingActionButton
    lateinit var swShowShiny: MaterialSwitch
    lateinit var btnShowMaps: ExtendedFloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        toolbar = findViewById(R.id.toolbar)
        imgPokemonSprite = findViewById(R.id.img_pokemon_sprite)
        txtPokemonDescription = findViewById(R.id.txt_pokemon_description)
        imgFirstPokemonType = findViewById(R.id.ic_first_pokemon_type)
        imgSecondPokemonType = findViewById(R.id.ic_second_pokemon_type)
        txtHeight = findViewById(R.id.txt_pokemon_height)
        txtWeight = findViewById(R.id.txt_pokemon_weight)
        btnRotateSprite = findViewById(R.id.btn_rotate_sprite)
        swShowShiny = findViewById(R.id.sw_show_shiny)
        btnShowMaps = findViewById(R.id.btn_show_map_pokemon)

        pokemonId = intent.getIntExtra("POKEMON_ID", 0)
        viewModel = ViewModelProvider(this)[PokedexViewModel::class.java]

        txtPokemonDescription.movementMethod = ScrollingMovementMethod()
        Log.e("the id pokemon is -->", pokemonId.toString())

        getPokemonInfo()

        btnRotateSprite.setOnClickListener {
            isRotated = !isRotated
            if (isShiny)
                rotateSprite(frontShinySprite, backShinySprite)
            else
                rotateSprite(frontSprite, backSprite)
        }

        swShowShiny.setOnCheckedChangeListener { buttonView, isChecked ->
            isShiny = isChecked
            if (isChecked) {
                rotateSprite(frontShinySprite, backShinySprite)
            } else {
                rotateSprite(frontSprite, backSprite)
            }
        }

        btnShowMaps.setOnClickListener {
            val intent = Intent(this, MapsPokemonActivity::class.java)
            startActivity(intent)
        }
    }

    fun getPokemonInfo() {
        viewModel.getPokemonDetail(pokemonId)
        viewModel.pokemonInfo.observe(this) { pokemon ->
            Log.e("pokemon info -->", pokemon.name)
            toolbar.setTitle(pokemon.name.capitalizeFirstLetterOfWord())

            frontSprite = pokemon.sprites.frontDefault.toString()
            backSprite = pokemon.sprites.backDefault.toString()

            frontShinySprite = pokemon.sprites.frontShiny.toString()
            backShinySprite = pokemon.sprites.backShiny.toString()

            rotateSprite(frontSprite, backSprite)

            val titleHeight = "<b>" + "Tamaño: " + "</b>"
            val titleWeight = "<b>" + "Pesos: " + "</b>"
            val pokemonHeight = Html.fromHtml(titleHeight + "${pokemon.height}mts.", 0)
            val pokemonWeight = Html.fromHtml(titleWeight + "${pokemon.weight}kg.", 0)
            txtHeight.text = pokemonHeight
            txtWeight.text = pokemonWeight

            val pokemonTypes = pokemon.types.map { it.type.name }
            Log.e("pokemon types --->", pokemonTypes.toString())

            val principalType: String
            val nameFirstType: String
            val typeImageResource: Int

            if (pokemonTypes.size == 2) {
                principalType = pokemonTypes[0]
                nameFirstType = "ic_$principalType"
                typeImageResource =
                    this.resources.getIdentifier(nameFirstType, "drawable", this.packageName)
                imgFirstPokemonType.setImageResource(typeImageResource)

                val secondType = pokemonTypes[1]
                val nameSecondType = "ic_$secondType"
                val secondTypeImageResource =
                    this.resources.getIdentifier(nameSecondType, "drawable", this.packageName)

                imgSecondPokemonType.setImageResource(secondTypeImageResource)
            } else {
                principalType = pokemonTypes[0]
                nameFirstType = "ic_$principalType"
                typeImageResource =
                    this.resources.getIdentifier(nameFirstType, "drawable", this.packageName)
                imgFirstPokemonType.setImageResource(typeImageResource)
                imgSecondPokemonType.visibility = View.GONE
            }

            getPokemonDescription()

        }
    }

    fun rotateSprite(front: String, back: String) {
        if (isRotated) {
            Picasso.get().load(back).into(imgPokemonSprite)
        } else {
            Picasso.get().load(front).into(imgPokemonSprite)
        }
    }

    fun getPokemonDescription() {
        viewModel.getPokemonDescription(pokemonId)
        viewModel.pokemonDescription.observe(this) { pokemon ->
            val spanishEntires = pokemon.flavorTextEntires.filter { it.lenguage.name == "es" }
            val spanishDescription = spanishEntires.firstOrNull()?.flavorText
            txtPokemonDescription.text = spanishDescription
        }
    }
}