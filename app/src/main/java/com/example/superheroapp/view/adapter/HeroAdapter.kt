package com.example.superheroapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.superheroapp.R
import com.example.superheroapp.model.Superhero
import com.squareup.picasso.Picasso

class HeroAdapter : RecyclerView.Adapter<HeroAdapter.ViewHolder>() {
    var sesionesProgramadas: MutableList<Superhero>  = ArrayList()
    lateinit var context: Context
    lateinit var listener: (Superhero) -> Unit

    fun HeroAdapter(superheros: MutableList<Superhero>, context: Context, listener: (Superhero) -> Unit){
        this.sesionesProgramadas = superheros
        this.context = context
        this.listener = listener
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = sesionesProgramadas.get(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_hero, parent, false))
    }
    override fun getItemCount(): Int {
        return sesionesProgramadas.size
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById(R.id.name) as TextView
        val image = view.findViewById(R.id.image) as ImageView
        fun bind(superhero: Superhero){
            name.text = superhero.name
            if (superhero.image != null && superhero.image.url != null) {
                Picasso.get()
                    .load(superhero.image.url)
                    .placeholder(R.drawable.empty)
                    .error(R.drawable.empty)
                    .into(image)
            }
        }
    }
}