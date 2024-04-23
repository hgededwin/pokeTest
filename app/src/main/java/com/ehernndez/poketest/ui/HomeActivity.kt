package com.ehernndez.poketest.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ehernndez.poketest.R
import com.ehernndez.poketest.ui.fragments.HomeFragment
import com.ehernndez.poketest.ui.fragments.PokedexFragment
import com.ehernndez.poketest.ui.fragments.PokemonFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    lateinit var navigationView: BottomNavigationView
    lateinit var toolbar: MaterialToolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigationView = findViewById(R.id.navigation_view)
        toolbar = findViewById(R.id.home_toolbar)

        // setting first fragment to show when load the home activity
        replaceFragment(HomeFragment())

        navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.item_pokedex -> {
                    replaceFragment(PokedexFragment())
                    true
                }

                R.id.item_new_pokemon -> {
                    replaceFragment(PokemonFragment())
                    true
                }

                else -> false
            }
        }

        toolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.item_settings -> {
                    Toast.makeText(this@HomeActivity, "Settings section is not working", Toast.LENGTH_LONG).show()

                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment).commit()
        }
    }
}