package com.example.launcher.ui.landmarks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.launcher.LandmarkAdapter
import com.example.launcher.R
import com.example.launcher.databinding.ActivityLandmarkMapCardsBinding
import com.example.launcher.model.LandmarkData

class LandmarkMapCardsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandmarkMapCardsBinding
    private lateinit var adapter: LandmarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandmarkMapCardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.landmarks)
    }

    private fun setupRecyclerView() {
        adapter = LandmarkAdapter(LandmarkData.landmarks) { landmark ->
            // Handle landmark click
        }
        binding.recyclerViewLandmarkCards.apply {
            setLayoutManager(LinearLayoutManager(this@LandmarkMapCardsActivity))
            setAdapter(this@LandmarkMapCardsActivity.adapter)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
} 