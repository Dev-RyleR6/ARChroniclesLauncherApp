package com.example.launcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.launcher.model.LandmarkData
import com.example.launcher.databinding.ActivityLandmarksBinding
import com.example.launcher.model.Landmark
import android.content.Intent
import android.widget.Toast

class LandmarksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandmarksBinding
    private lateinit var adapter: LandmarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandmarksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = LandmarkAdapter(LandmarkData.landmarks) { landmark ->
            // Show landmark details
            showLandmarkDetails(landmark)
        }

        binding.recyclerViewLandmarkCards.apply {
            layoutManager = LinearLayoutManager(this@LandmarksActivity)
            adapter = this@LandmarksActivity.adapter
        }
    }

    private fun showLandmarkDetails(landmark: Landmark) {
        // For now, show a toast with the landmark details
        // You can replace this with a proper detail view or dialog
        val message = "${landmark.name}\n${landmark.description}"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        
        // TODO: Create a LandmarkDetailActivity or Dialog to show more details
        // val intent = Intent(this, LandmarkDetailActivity::class.java).apply {
        //     putExtra("landmark_id", landmark.id)
        // }
        // startActivity(intent)
    }
} 