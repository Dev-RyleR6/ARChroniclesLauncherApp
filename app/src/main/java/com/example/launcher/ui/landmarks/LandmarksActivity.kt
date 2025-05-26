package com.example.launcher.ui.landmarks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.launcher.databinding.ActivityLandmarksBinding
import com.example.launcher.LandmarkAdapter
import com.example.launcher.model.LandmarkData

class LandmarksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandmarksBinding
    private lateinit var adapter: LandmarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandmarksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setupRecyclerView() {
        adapter = LandmarkAdapter(LandmarkData.landmarks) { landmark ->
            // Handle landmark click
        }
        binding.recyclerViewLandmarkCards.apply {
            layoutManager = LinearLayoutManager(this@LandmarksActivity)
            adapter = this@LandmarksActivity.adapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
} 