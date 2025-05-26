package com.example.launcher.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.launcher.LandmarkMapCardAdapter
import com.example.launcher.R
import com.example.launcher.model.LandmarkData
import com.example.launcher.ui.landmarks.LandmarkListFragmentDirections
import com.google.android.material.appbar.MaterialToolbar

class LandmarkMapCardsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LandmarkMapCardAdapter
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmark_map_cards)

        // Set up toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        // Set up Navigation
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Initialize RecyclerView with grid layout
        recyclerView = findViewById(R.id.recyclerViewLandmarkCards)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // 2 columns for cards
        adapter = LandmarkMapCardAdapter(LandmarkData.landmarks) { landmark ->
            // Navigate to LandmarkDetailFragment using Safe Args
            val action = LandmarkListFragmentDirections.actionLandmarkListToDetail(
                landmarkTitle = landmark.name,
                landmarkDescription = landmark.description,
                landmarkImageResId = landmark.imageResId,
                latitude = landmark.latitude.toFloat(),
                longitude = landmark.longitude.toFloat()
            )
            navController.navigate(action)
        }
        recyclerView.adapter = adapter
    }
} 