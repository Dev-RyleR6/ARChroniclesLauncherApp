package com.example.launcher

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.launcher.R

class LauncherCardAdapter(
    private val context: Context,
    private val cards: List<CardItem>
) : RecyclerView.Adapter<LauncherCardAdapter.CardViewHolder>() {

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.cardTitle)
        val description: TextView = view.findViewById(R.id.cardDescription)
        val icon: ImageView = view.findViewById(R.id.cardIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_launcher_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]
        holder.title.text = card.name
        holder.description.text = card.description
        holder.icon.setImageResource(card.iconResId)
        holder.itemView.setOnClickListener { card.onClick() }
    }

    override fun getItemCount() = cards.size
}