package com.ehernndez.poketest.data.api.models

import com.google.gson.annotations.SerializedName

data class PokemonDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("base_experience") val baseExperience: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("sprites") val sprites: Sprites,
    @SerializedName("types") val types: List<Type>,
    @SerializedName("flavor_text_entries") val flavorTextEntires: List<FlavorTextEntry>
)

data class Sprites(
    @SerializedName("back_default") val backDefault: String?,
    @SerializedName("back_female") val backFemale: String?,
    @SerializedName("back_shiny") val backShiny: String?,
    @SerializedName("back_shiny_female") val backShinyFemale: String?,
    @SerializedName("front_default") val frontDefault: String?,
    @SerializedName("front_female") val frontFemale: String?,
    @SerializedName("front_shiny") val frontShiny: String?,
    @SerializedName("front_shiny_female") val frontShinyFemale: String?)

data class Type(
    @SerializedName("slot") val slot: Int,
    @SerializedName("type") val type: TypeInfo)


data class TypeInfo(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String)


data class FlavorTextEntry(
    @SerializedName("flavor_text") val flavorText: String,
    @SerializedName("language") val lenguage: Lenguage)


data class Lenguage(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String)