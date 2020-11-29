package com.miramicodigo.tecnm.view

import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.miramicodigo.tecnm.R
import com.miramicodigo.tecnm.model.Pokemon
import kotlinx.android.synthetic.main.activity_detalle.*

class DetalleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val pokemon = intent.getSerializableExtra("poke") as Pokemon

        if(pokemon != null) {
            Glide.with(this)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemon.number}.png")
                    .into(ivDetalleImagen)
            val font = Typeface.createFromAsset(assets, "product_sans_bold.ttf")
            tvDetalleNombre.typeface = font

            tvDetalleNombre.text = pokemon.name

        }
    }

}