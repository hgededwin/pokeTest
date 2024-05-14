package com.ehernndez.poketest.data.api.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ehernndez.poketest.data.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokedexViewModel : ViewModel() {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: ApiService = retrofit.create(ApiService::class.java)

    val pokemonList = MutableLiveData<List<Pokedex>>()
    val pokemonInfo = MutableLiveData<PokemonDetail>()
    val pokemonDescription = MutableLiveData<PokemonDetail>()

    fun getPodexInfo() {
        val call = service.getPokedexList(151, 0)
        call.enqueue(object : Callback<PokedexResponse> {
            override fun onResponse(
                call: Call<PokedexResponse>,
                response: Response<PokedexResponse>
            ) {
                response.body()?.results.let { list ->
                    pokemonList.postValue(list)
                }
            }

            override fun onFailure(call: Call<PokedexResponse>, t: Throwable) {
                call.cancel()
            }
        })
    }

    fun getPokemonDetail(id: Int) {
        val call = service.getPokemonInfo(id)
        call.enqueue(object : Callback<PokemonDetail> {
            override fun onResponse(call: Call<PokemonDetail>, response: Response<PokemonDetail>) {
                response.body()?.let { pokemon ->
                    pokemonInfo.postValue(pokemon)
                }
            }

            override fun onFailure(call: Call<PokemonDetail>, t: Throwable) {
                call.cancel()
            }
        })
    }


    fun getPokemonDescription(id: Int) {
        val call = service.getPokemonDescription(id)
        call.enqueue(object : Callback<PokemonDetail> {
            override fun onResponse(call: Call<PokemonDetail>, response: Response<PokemonDetail>) {
                response.body().let { pokemon ->
                    pokemonDescription.postValue(pokemon)
                }
            }

            override fun onFailure(call: Call<PokemonDetail>, t: Throwable) {
                call.cancel()
            }
        })
    }
}