package com.miramicodigo.tecnm.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miramicodigo.tecnm.R
import com.miramicodigo.tecnm.model.PokemonResponse
import com.miramicodigo.tecnm.service.PokeInterface
import com.miramicodigo.tecnm.view.adapter.PokemonAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var listaPokemonAdapter: PokemonAdapter
    var desdePosicion = 0
    var cantidadAObtener = 20
    var sePuedeCargar: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listaPokemonAdapter = PokemonAdapter(this)
        recyclerView.adapter = listaPokemonAdapter
        recyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager

        retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        sePuedeCargar = true
        desdePosicion = 0
        obtenerDatos(desdePosicion)

        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy > 0) {
                    val visibleCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    if(sePuedeCargar) {
                        if(visibleCount + pastVisibleItems >= totalItemCount) {
                            sePuedeCargar = false
                            desdePosicion+=20
                            obtenerDatos(desdePosicion)
                        }
                    }
                }
            }
        })

    }

    private fun obtenerDatos(offset: Int) {
        val service = retrofit.create(PokeInterface::class.java)
        val pokemonResponseCall = service.obtenerListaPokemon(offset, cantidadAObtener)

        llCarga.visibility = View.VISIBLE

        pokemonResponseCall.enqueue(object: Callback<PokemonResponse> {
            override fun onResponse(call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                llCarga.visibility = View.INVISIBLE
                if(response.isSuccessful) {
                    sePuedeCargar = true
                    val pokemonResponse = response.body()
                    val listaPokemon = pokemonResponse?.results
                    listaPokemon?.let { listaPokemonAdapter.adicionarListaPokemon(it) }
                }
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                llCarga.visibility = View.INVISIBLE
            }

        })
    }



}