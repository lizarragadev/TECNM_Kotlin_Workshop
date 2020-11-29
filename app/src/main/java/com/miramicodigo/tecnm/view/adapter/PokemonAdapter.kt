package com.miramicodigo.tecnm.view.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.miramicodigo.tecnm.R
import com.miramicodigo.tecnm.model.Pokemon
import com.miramicodigo.tecnm.view.DetalleActivity

class PokemonAdapter(private val context: Context) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    private val dataset: ArrayList<Pokemon> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_poke, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = dataset[position]

        val fontBold = Typeface.createFromAsset(context.assets, "product_sans_bold.ttf")
        holder.nombreTextView.typeface = fontBold

        holder.nombreTextView.text = p.name

        val requestOption = RequestOptions()
                .placeholder(R.drawable.pokeball).centerCrop()

        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${p.number}.png")
                .apply(requestOption)
                .into(holder.fotoImageView)

    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun adicionarListaPokemon(listaPokemon: ArrayList<Pokemon>) {
        dataset.addAll(listaPokemon)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fotoImageView: ImageView = itemView.findViewById(R.id.ivImagen) as ImageView
        val nombreTextView: TextView = itemView.findViewById(R.id.tvNombre)

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, DetalleActivity::class.java)
                intent.putExtra("poke", dataset[adapterPosition])
                context.startActivity(intent)
            }
        }
    }
}