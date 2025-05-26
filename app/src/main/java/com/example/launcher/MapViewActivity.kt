package com.example.launcher

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapViewActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    private lateinit var myLocationOverlay: MyLocationNewOverlay
    private lateinit var compassOverlay: CompassOverlay
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_view)

        // Initialize OSMDroid configuration
        Configuration.getInstance().userAgentValue = packageName

        // Initialize map
        mapView = findViewById(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)

        // Set initial view to Dumaguete City
        val startPoint = GeoPoint(9.3117, 123.3050)
        mapView.controller.setZoom(15.0)
        mapView.controller.setCenter(startPoint)

        // Add location overlay
        myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mapView)
        myLocationOverlay.enableMyLocation()
        mapView.overlays.add(myLocationOverlay)

        // Add compass overlay
        compassOverlay = CompassOverlay(this, InternalCompassOrientationProvider(this), mapView)
        compassOverlay.enableCompass()
        mapView.overlays.add(compassOverlay)

        // Set up map controls
        findViewById<FloatingActionButton>(R.id.fabMyLocation).setOnClickListener {
            if (checkLocationPermission()) {
                navigateToMyLocation()
            } else {
                requestLocationPermission()
            }
        }

        findViewById<FloatingActionButton>(R.id.fabZoomIn).setOnClickListener {
            mapView.controller.zoomIn()
        }

        findViewById<FloatingActionButton>(R.id.fabZoomOut).setOnClickListener {
            mapView.controller.zoomOut()
        }

        // Add a marker at a specific location
        val marker = Marker(mapView).apply {
            position = GeoPoint(9.3117, 123.3050)
            title = "Dumaguete City"
            snippet = "Welcome to Dumaguete!"
        }
        mapView.overlays.add(marker)
    }

    private fun navigateToMyLocation() {
        if (myLocationOverlay.myLocation != null) {
            mapView.controller.animateTo(myLocationOverlay.myLocation)
        } else {
            Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                navigateToMyLocation()
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
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