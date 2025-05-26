package com.example.launcher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CardItemAdapter(
    val items: List<CardItem>,
    private val onItemClick: (CardItem) -> Unit = {}
) : RecyclerView.Adapter<CardItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iconView: ImageView = view.findViewById(R.id.cardIcon)
        val titleView: TextView = view.findViewById(R.id.cardTitle)
        val descriptionView: TextView = view.findViewById(R.id.cardDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_launcher_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.iconView.setImageResource(item.iconResId)
        holder.titleView.text = item.name
        holder.descriptionView.text = item.description

        holder.itemView.setOnClickListener {
            onItemClick(item)
            item.onClick()
        }
    }

    override fun getItemCount() = items.size
} 