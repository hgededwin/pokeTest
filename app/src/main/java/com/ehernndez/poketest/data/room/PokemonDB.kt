package com.ehernndez.poketest.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Pokemon::class], version = 1)
abstract class PokemonDB : RoomDatabase() {
    abstract fun daoPokemon(): DAOPokemon
}