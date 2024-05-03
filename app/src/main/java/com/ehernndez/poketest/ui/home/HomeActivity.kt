package com.ehernndez.poketest.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ehernndez.poketest.R
import com.ehernndez.poketest.ui.home.fragments.HomeFragment
import com.ehernndez.poketest.ui.home.fragments.PokedexFragment
import com.ehernndez.poketest.ui.home.fragments.PokemonFragment
import com.ehernndez.poketest.utils.Utils
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
            when (menuItem.itemId) {
                R.id.item_settings -> {
                    Utils().createIntent(this@HomeActivity, SettingsActivity())

                    // Calling launch coroutine function
                    // helloWorldCoroutine()

                    // Calling async coroutine function
                    //helloWorldCoroutineAsync()

                    // Calling differents types of dispatchers
                    // dispatchers()

                    // calling suspend fun function
                    // callingDoSomething()
                    true
                }

                else -> false
            }
        }
    }

    fun helloWorldCoroutine() {
        GlobalScope.launch {
            delay(10000L)
            Log.e("[Coroutines] --->", "Hello world")

        }
        Log.e("[Thread] --->", "Hello world from thread")
        Thread.sleep(5000L)
    }

    fun helloWorldCoroutineAsync() {
        GlobalScope.launch {
            val result = async {
                calculateResult()
            }
            Log.e("[Coroutines] --->", "calculated result: ${result.await()}")
        }
        Thread.sleep(2000L)
    }

    suspend fun calculateResult(): Int {
        delay(3000L)
        return 100
    }

    fun dispatchers() = runBlocking {
        launch(Dispatchers.IO) {
            Log.e("[Coroutines] --->", "Disptachers.IO --> ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Default) {
            Log.e("[Coroutines] --->", "Dispatches.Default --> ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Main) {
            Log.e(
                "[Coroutines] --->",
                "Dispatchers.Main --> ${Thread.currentThread().name}"
            )
        }
    }

    suspend fun doSomething() {
        delay(5000L)
        Log.e("[Coroutines] --->", "Calling doSomething function")
    }

    fun callingDoSomething() = runBlocking {
        launch {
            doSomething()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment).commit()
        }
    }
}