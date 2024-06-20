package com.ehernndez.poketest.data.persistantData

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.room.Room
import com.ehernndez.poketest.data.room.PokemonDB
import com.ehernndez.poketest.ui.connection.NoConnectionActivity
import com.ehernndez.poketest.utils.NetworkConnection
import com.google.firebase.analytics.FirebaseAnalytics

class Data : Application() {
    companion object {
        lateinit var shared: Shared
        lateinit var room: PokemonDB
        lateinit var firebaseAnalytics: FirebaseAnalytics
    }

    override fun onCreate() {
        super.onCreate()
        shared = Shared(applicationContext)
        room = Room.databaseBuilder(applicationContext, PokemonDB::class.java, "pokeTest_database").build()

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observeForever { isConnected ->
            if (isConnected) {
                Toast.makeText(this, "Conectado", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(applicationContext, NoConnectionActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
    }
}