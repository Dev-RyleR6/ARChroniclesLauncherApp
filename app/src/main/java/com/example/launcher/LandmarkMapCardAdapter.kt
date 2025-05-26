package com.example.launcher

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.launcher.databinding.ItemLandmarkMapCardBinding
import com.example.launcher.model.Landmark

class LandmarkMapCardAdapter(
    private val landmarks: List<Landmark>,
    private val onItemClick: (Landmark) -> Unit
) : RecyclerView.Adapter<LandmarkMapCardAdapter.LandmarkViewHolder>() {

    class LandmarkViewHolder(private val binding: ItemLandmarkMapCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(landmark: Landmark, onItemClick: (Landmark) -> Unit) {
            binding.landmarkImage.setImageResource(landmark.imageResId)
            binding.landmarkName.text = landmark.name
            binding.landmarkDescription.text = landmark.description
            binding.root.setOnClickListener { onItemClick(landmark) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandmarkViewHolder {
        val binding = ItemLandmarkMapCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LandmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LandmarkViewHolder, position: Int) {
        holder.bind(landmarks[position], onItemClick)
    }

    override fun getItemCount() = landmarks.size
} 