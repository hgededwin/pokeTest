package com.ehernndez.poketest.data.persistantData

import android.app.Application
import androidx.room.Room
import com.ehernndez.poketest.data.room.PokemonDB

class Data : Application() {
    companion object {
        lateinit var shared: Shared
        lateinit var room: PokemonDB
    }

    override fun onCreate() {
        super.onCreate()
        shared = Shared(applicationContext)
        room = Room.databaseBuilder(applicationContext, PokemonDB::class.java, "pokeTest_database").build()
    }
}