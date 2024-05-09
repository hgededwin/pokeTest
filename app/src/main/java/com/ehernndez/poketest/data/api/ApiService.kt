package com.ehernndez.poketest.data.api

import com.ehernndez.poketest.data.api.models.PokedexResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    fun getPokedexList(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<PokedexResponse>
}