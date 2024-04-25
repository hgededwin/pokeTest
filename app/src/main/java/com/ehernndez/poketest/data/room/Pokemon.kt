package com.ehernndez.poketest.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_table")
data class Pokemon(
    @PrimaryKey var pokemonName: String,
    @ColumnInfo(name = "pokemonType") var pokemonType: String
)
