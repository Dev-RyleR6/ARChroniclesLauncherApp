package com.example.launcher

import android.location.Location
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.launcher.databinding.ItemLandmarkBinding
import com.example.launcher.model.Landmark

class LandmarkAdapter(
    private val landmarks: List<Landmark>,
    private val onItemClick: (Landmark) -> Unit
) : RecyclerView.Adapter<LandmarkAdapter.LandmarkViewHolder>() {

    private var currentLocation: Location? = null

    fun updateLocation(latitude: Double, longitude: Double) {
        currentLocation = Location("").apply {
            this.latitude = latitude
            this.longitude = longitude
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandmarkViewHolder {
        val binding = ItemLandmarkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LandmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LandmarkViewHolder, position: Int) {
        val landmark = landmarks[position]
        holder.bind(landmark)
    }

    override fun getItemCount() = landmarks.size

    inner class LandmarkViewHolder(
        private val binding: ItemLandmarkBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(landmarks[position])
                }
            }
        }

        fun bind(landmark: Landmark) {
            binding.apply {
                landmarkName.text = landmark.name
                landmarkDescription.text = landmark.description
                
                // Set image if available
                if (landmark.imageResId != 0) {
                    landmarkImage.setImageResource(landmark.imageResId)
                }

                // Calculate and display distance
                currentLocation?.let { location ->
                    val landmarkLocation = Location("").apply {
                        latitude = landmark.latitude
                        longitude = landmark.longitude
                    }
                    val distanceInMeters = location.distanceTo(landmarkLocation)
                    val distanceInKm = distanceInMeters / 1000
                    val formattedDistance = if (distanceInKm < 1) {
                        root.context.getString(R.string.distance_meters, distanceInMeters)
                    } else {
                        root.context.getString(R.string.distance_kilometers, distanceInKm)
                    }
                    landmarkDistance.text = formattedDistance
                } ?: run {
                    landmarkDistance.text = root.context.getString(R.string.distance_unknown)
                }
            }
        }
    }
}
