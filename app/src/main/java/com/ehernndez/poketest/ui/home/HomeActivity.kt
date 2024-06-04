package com.ehernndez.poketest.ui.home

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.persistantData.Data
import com.ehernndez.poketest.ui.home.fragments.HomeFragment
import com.ehernndez.poketest.ui.home.fragments.PokedexFragment
import com.ehernndez.poketest.ui.home.fragments.PokemonFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {
    lateinit var navigationView: BottomNavigationView
    lateinit var toolbar: MaterialToolbar

    lateinit var actionDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var drawerNavigation: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigationView = findViewById(R.id.navigation_view)
        toolbar = findViewById(R.id.home_toolbar)
        drawerLayout = findViewById(R.id.drawer_layout_home)
        drawerNavigation = findViewById(R.id.drawer_navigation_view)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // setting first fragment to show when load the home activity
        replaceFragment(HomeFragment())

        createBottomNavigationMenu()
        createDrawerMenu()
    }

    private fun createDrawerMenu() {
        actionDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.txt_open_drawer, R.string.txt_close_drawer)
        drawerLayout.addDrawerListener(actionDrawerToggle)
        actionDrawerToggle.syncState()

        drawerNavigation.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_notifications -> {
                    Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.item_share -> {
                    Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.item_info -> {
                    Toast.makeText(this, "Info", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }

        val headerView = drawerNavigation.getHeaderView(0)
        val txtUserName = headerView.findViewById<TextView>(R.id.txt_username)
        val username = Data.shared.userName + " " + Data.shared.lastName
        txtUserName.text = username

        val txtBirthday = headerView.findViewById<TextView>(R.id.txt_birthday)
        txtBirthday.text = Data.shared.bornDate

        val txtEmail = headerView.findViewById<TextView>(R.id.txt_email)
        txtEmail.text = Data.shared.email


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionDrawerToggle.onOptionsItemSelected(item)) {
            return false
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createBottomNavigationMenu() {
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
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment).commit()
        }
    }
}


// this code is used to open SettingsActivity
/* toolbar.setOnMenuItemClickListener { menuItem ->
     when (menuItem.itemId) {
         R.id.item_settings -> {
             Utils().createIntent(this@HomeActivity, SettingsActivity())
             true
         }

         else -> false
     }
 } */