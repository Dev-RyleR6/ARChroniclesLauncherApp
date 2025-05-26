package com.example.launcher.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.launcher.R
import com.example.launcher.data.PreferencesManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import com.google.android.material.appbar.MaterialToolbar

class LandmarkMapActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    private lateinit var myLocationOverlay: MyLocationNewOverlay
    private lateinit var compassOverlay: CompassOverlay
    private lateinit var preferencesManager: PreferencesManager

    companion object {
        const val EXTRA_LANDMARK_TITLE = "landmark_title"
        const val EXTRA_LANDMARK_DESCRIPTION = "landmark_description"
        const val EXTRA_LANDMARK_LATITUDE = "landmark_latitude"
        const val EXTRA_LANDMARK_LONGITUDE = "landmark_longitude"
        const val EXTRA_LANDMARK_IMAGE_RES = "landmark_image_res"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmark_map)

        // Initialize preferences manager
        preferencesManager = PreferencesManager(applicationContext)

        // Initialize OSMdroid configuration
        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences("osmdroid", MODE_PRIVATE)
        )

        // Get landmark data from intent
        var title = intent.getStringExtra(EXTRA_LANDMARK_TITLE)
        if (title == null) {
            finish()
            return
        }
        val description = intent.getStringExtra(EXTRA_LANDMARK_DESCRIPTION) ?: ""
        val latitude = intent.getDoubleExtra(EXTRA_LANDMARK_LATITUDE, 0.0)
        val longitude = intent.getDoubleExtra(EXTRA_LANDMARK_LONGITUDE, 0.0)
        val imageResId = intent.getIntExtra(EXTRA_LANDMARK_IMAGE_RES, 0)

        // Set toolbar title
        findViewById<MaterialToolbar>(R.id.topAppBar).title = title

        // Initialize map view
        mapView = findViewById(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)

        // Set initial map position to the landmark
        val landmarkPoint = GeoPoint(latitude, longitude)
        mapView.controller.setZoom(16.0) // Closer zoom for single landmark
        mapView.controller.setCenter(landmarkPoint)

        // Initialize location overlay
        myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mapView)
        myLocationOverlay.enableMyLocation()
        mapView.overlays.add(myLocationOverlay)

        // Initialize compass overlay
        compassOverlay = CompassOverlay(this, InternalCompassOrientationProvider(this), mapView)
        compassOverlay.enableCompass()
        mapView.overlays.add(compassOverlay)

        // Add marker for the landmark
        val marker = Marker(mapView).apply {
            position = landmarkPoint
            title = title
            snippet = description
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        }
        mapView.overlays.add(marker)

        // Set up map control buttons
        setupMapControls()

        // Save last known location
        lifecycleScope.launch {
            preferencesManager.setLastLocation(latitude, longitude)
        }
    }

    private fun setupMapControls() {
        findViewById<FloatingActionButton>(R.id.fabMyLocation).setOnClickListener {
            myLocationOverlay.enableFollowLocation()
            myLocationOverlay.enableMyLocation()
            mapView.controller.animateTo(myLocationOverlay.myLocation)
        }

        findViewById<FloatingActionButton>(R.id.fabZoomIn).setOnClickListener {
            mapView.controller.zoomIn()
        }

        findViewById<FloatingActionButton>(R.id.fabZoomOut).setOnClickListener {
            mapView.controller.zoomOut()
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
} 