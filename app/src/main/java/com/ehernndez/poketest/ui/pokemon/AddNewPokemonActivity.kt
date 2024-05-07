package com.ehernndez.poketest.ui.pokemon

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.persistantData.Data
import com.ehernndez.poketest.data.room.Pokemon
import com.ehernndez.poketest.data.room.PokemonDB
import com.ehernndez.poketest.utils.Utils
import com.ehernndez.poketest.utils.capitalizeFirstLetterOfWord
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddNewPokemonActivity : AppCompatActivity() {
    lateinit var dropDownPokemonType: AutoCompleteTextView
    lateinit var edtxtPokemonName: TextInputEditText
    lateinit var containerEdtxtPokemonName: TextInputLayout
    lateinit var containerDropdownPokemonType: TextInputLayout
    lateinit var btnAddPokemon: Button
    lateinit var toolbar: MaterialToolbar
    lateinit var room: PokemonDB
    lateinit var txtSubtitle: TextView
    lateinit var pokemonNameSelected: String
    lateinit var pokemonTypeSelected: String
    lateinit var btnBack: ImageView

    var pokemonTypes = listOf<String>()
    var pokemonList = mutableListOf<Pokemon>()
    var isUpdateScreenFlow = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_pokemon)

        dropDownPokemonType = findViewById(R.id.dropdown_pokemon_type)
        edtxtPokemonName = findViewById(R.id.edtxt_pokemon_name)
        containerEdtxtPokemonName = findViewById(R.id.container_edtxt_pokemon_name)
        containerDropdownPokemonType = findViewById(R.id.container_dropdown_type)
        btnAddPokemon = findViewById(R.id.btn_add_pokemon)
        toolbar = findViewById(R.id.toolbar)
        txtSubtitle = findViewById(R.id.txt_subtitle)
        btnBack = findViewById(R.id.btn_back)

        isUpdateScreenFlow =
            intent.getBooleanExtra(getString(R.string.txt_update_screen_flow_tag), false)
        pokemonNameSelected =
            intent.getStringExtra(getString(R.string.txt_pokemon_name_selected_flag)).toString()
        pokemonTypeSelected =
            intent.getStringExtra(getString(R.string.txt_pokemon_type_selected_flag)).toString()

        if (isUpdateScreenFlow) {
            toolbar.setTitle(getString(R.string.txt_title_update))
            txtSubtitle.text = getString(R.string.txt_subtitle_update)
            btnAddPokemon.text = getString(R.string.txt_btn_update)

            edtxtPokemonName.setText(pokemonNameSelected.capitalizeFirstLetterOfWord())
            dropDownPokemonType.setText(pokemonTypeSelected.capitalizeFirstLetterOfWord())

        }

        room = Data.room

        getPokemonList(room)

        pokemonTypes = listOf(
            "Agua",
            "Acero",
            "Bicho",
            "Dragón",
            "Eléctrico",
            "Fantasma",
            "Fuego",
            "Hada",
            "Hielo",
            "Lucha",
            "Normal",
            "Planta",
            "Psíquico",
            "Roca",
            "Siniestro",
            "Tierra",
            "Veneno",
            "Volador"
        )

        val adapter = ArrayAdapter(this, R.layout.dropdown_item, pokemonTypes)
        dropDownPokemonType.setAdapter(adapter)

        edtxtPokemonName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (edtxtPokemonName.text!!.isEmpty()) {
                    containerEdtxtPokemonName.error = "Es necesario escribir el nombre"
                } else {
                    containerEdtxtPokemonName.error = null
                }

                validateInputs()
            }
        })

        dropDownPokemonType.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (dropDownPokemonType.text!!.isEmpty()) {
                    containerDropdownPokemonType.error = "Es necesario seleccionar una opción"
                } else {
                    containerDropdownPokemonType.error = null
                }
                validateInputs()
            }
        })

        btnAddPokemon.setOnClickListener {
            val pokemon =
                Pokemon(
                    edtxtPokemonName.text.toString().lowercase(),
                    dropDownPokemonType.text.toString().lowercase()
                )

            if (pokemonList.contains(pokemon)) {
                Snackbar.make(it, "Este pokémon ya existe", Snackbar.LENGTH_LONG).show()
            } else {
                addNewPokemon(it, room, pokemon)
            }
        }

        btnBack.setOnClickListener {

            val message = if (isUpdateScreenFlow) {
                "¿Estás seguro que desas cancelar la actualización de los datos del Pókemon ${pokemonNameSelected.capitalizeFirstLetterOfWord()}?"
            } else {
                getString(R.string.txt_question_cancel_pokemon_register)
            }

            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setPositiveButton(getString(R.string.txt_alert_btn_stay)) { dialog, which ->
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.txt_alert_btn_exit)) { dialog, which ->
                    finish()
                }
                .show()
        }
    }

    fun addNewPokemon(view: View, room: PokemonDB, pokemon: Pokemon) {
        lifecycleScope.launch {
            room.daoPokemon().setNewPokemon(pokemon)

            getPokemonList(room)
            Snackbar.make(view, "El Pókemon ha sido agregado exitosamente", Snackbar.LENGTH_LONG)
                .show()
            delay(2000)
            finish()
        }
    }

    fun getPokemonList(room: PokemonDB) {
        lifecycleScope.launch {
            pokemonList = room.daoPokemon().getPokemonList()
            Log.e("---> PokemonList", pokemonList.toString())
        }
    }

    fun validateInputs() {
        if (edtxtPokemonName.text!!.isEmpty() || dropDownPokemonType.text!!.isEmpty()) {
            Utils().enableButton(
                this@AddNewPokemonActivity,
                btnAddPokemon,
                isEnable = false,
                isVariant = true
            )
        } else {
            Utils().enableButton(
                this@AddNewPokemonActivity,
                btnAddPokemon,
                isEnable = true,
                isVariant = true
            )
        }
    }
}