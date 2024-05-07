package com.ehernndez.poketest.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DAOPokemon {
    @Insert
    suspend fun setNewPokemon(pokemon: Pokemon)

    @Query("SELECT * FROM pokemon_table")
    suspend fun getPokemonList(): MutableList<Pokemon>

    @Query("UPDATE pokemon_table set pokemonType = :pokemonType WHERE pokemonName = :pokemonName")
    suspend fun updatePokemonInfo(pokemonName: String, pokemonType: String)

    @Query("DELETE FROM pokemon_table WHERE pokemonName = :pokemonName")
    suspend fun deletePokemonInfo(pokemonName: String)

    @Query("SELECT * FROM pokemon_table WHERE pokemonName = :pokemonName")
    suspend fun getPokemonSelected(pokemonName: String): Pokemon
}