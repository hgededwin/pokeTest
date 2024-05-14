package com.ehernndez.poketest.data.api

import com.ehernndez.poketest.data.api.models.PokedexResponse
import com.ehernndez.poketest.data.api.models.PokemonDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    fun getPokedexList(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<PokedexResponse>

    @GET("pokemon/{id}")
    fun getPokemonInfo(@Path("id") id: Int): Call<PokemonDetail>

    @GET("pokemon-species/{id}")
    fun getPokemonDescription(@Path("id") id: Int): Call<PokemonDetail>
}