package com.example.launcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.launcher.model.LandmarkData
import com.example.launcher.databinding.ActivityLandmarkMapCardsBinding

class LandmarkMapCardsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandmarkMapCardsBinding
    private lateinit var adapter: LandmarkMapCardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandmarkMapCardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = LandmarkMapCardAdapter(LandmarkData.landmarks) { landmark ->
            // Handle landmark click
            // You can start a detail activity or show more information
        }

        binding.recyclerViewLandmarkCards.apply {
            layoutManager = LinearLayoutManager(this@LandmarkMapCardsActivity)
            adapter = this@LandmarkMapCardsActivity.adapter
        }
    }
} 