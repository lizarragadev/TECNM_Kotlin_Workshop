package com.miramicodigo.tecnm.model

import com.google.gson.annotations.SerializedName

class PokemonResponse {
    @SerializedName("results")
    var results: ArrayList<Pokemon>? = null
}