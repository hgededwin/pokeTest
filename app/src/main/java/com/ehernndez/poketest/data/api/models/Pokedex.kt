package com.ehernndez.poketest.data.api.models

import com.google.gson.annotations.SerializedName

data class Pokedex(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)