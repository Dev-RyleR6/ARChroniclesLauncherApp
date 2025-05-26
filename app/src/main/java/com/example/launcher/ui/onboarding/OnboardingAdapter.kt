package com.example.launcher.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.launcher.databinding.ItemOnboardingWelcomeBinding
import com.example.launcher.databinding.ItemOnboardingLandmarksBinding
import com.example.launcher.databinding.ItemOnboardingMapBinding

class OnboardingAdapter(private val items: List<OnboardingItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_WELCOME = 0
        private const val VIEW_TYPE_LANDMARKS = 1
        private const val VIEW_TYPE_MAP = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_WELCOME
            1 -> VIEW_TYPE_LANDMARKS
            2 -> VIEW_TYPE_MAP
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_WELCOME -> {
                val binding = ItemOnboardingWelcomeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                WelcomeViewHolder(binding)
            }
            VIEW_TYPE_LANDMARKS -> {
                val binding = ItemOnboardingLandmarksBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                LandmarksViewHolder(binding)
            }
            VIEW_TYPE_MAP -> {
                val binding = ItemOnboardingMapBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                MapViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WelcomeViewHolder -> holder.bind(items[position])
            is LandmarksViewHolder -> holder.bind(items[position])
            is MapViewHolder -> holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int = items.size

    class WelcomeViewHolder(private val binding: ItemOnboardingWelcomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OnboardingItem) {
            binding.imageViewWelcome.setImageResource(item.imageResId)
            binding.textViewWelcomeTitle.text = item.title
            binding.textViewWelcomeDescription.text = item.description
        }
    }

    class LandmarksViewHolder(private val binding: ItemOnboardingLandmarksBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OnboardingItem) {
            binding.imageViewLandmarks.setImageResource(item.imageResId)
            binding.textViewLandmarksTitle.text = item.title
            binding.textViewLandmarksDescription.text = item.description
        }
    }

    class MapViewHolder(private val binding: ItemOnboardingMapBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OnboardingItem) {
            binding.imageViewMap.setImageResource(item.imageResId)
            binding.textViewMapTitle.text = item.title
            binding.textViewMapDescription.text = item.description
        }
    }
} 