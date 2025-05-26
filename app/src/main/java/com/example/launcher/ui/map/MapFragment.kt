package com.example.launcher.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.launcher.R
import com.example.launcher.model.LandmarkData
import com.example.launcher.model.Landmark
import com.example.launcher.databinding.FragmentMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
import org.osmdroid.views.overlay.infowindow.InfoWindow
import java.text.DecimalFormat

class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var mapView: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var myLocationOverlay: MyLocationNewOverlay
    private lateinit var compassOverlay: CompassOverlay
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private var currentLocation: Location? = null
    private val markers = mutableListOf<Marker>()
    private val args by lazy {
        requireArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize OSMdroid configuration
        Configuration.getInstance().load(
            requireContext(),
            requireActivity().getSharedPreferences("osmdroid", 0)
        )

        setupMap()
        setupLandmarkMarkers()

        // Set up location overlay
        myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), mapView)
        myLocationOverlay.enableMyLocation()
        mapView.overlays.add(myLocationOverlay)

        // Set up compass overlay
        compassOverlay = CompassOverlay(requireContext(), InternalCompassOrientationProvider(requireContext()), mapView)
        compassOverlay.enableCompass()
        mapView.overlays.add(compassOverlay)

        // Set up FABs
        view.findViewById<FloatingActionButton>(R.id.fabMyLocation).setOnClickListener {
            if (checkLocationPermission()) {
                myLocationOverlay.enableMyLocation()
                mapView.controller.animateTo(myLocationOverlay.myLocation)
            } else {
                requestLocationPermission()
            }
        }

        view.findViewById<FloatingActionButton>(R.id.fabZoomIn).setOnClickListener {
            mapView.controller.zoomIn()
        }

        view.findViewById<FloatingActionButton>(R.id.fabZoomOut).setOnClickListener {
            mapView.controller.zoomOut()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (checkLocationPermission()) {
            getCurrentLocation()
        } else {
            requestLocationPermission()
        }
    }

    private fun setupMap() {
        mapView = binding.mapView
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)

        // Check if arguments were passed
        val lat = args.getFloat("latitude", 0.0f)
        val lon = args.getFloat("longitude", 0.0f)
        if (lat != 0.0f && lon != 0.0f) {
            // First zoom out to show context
            mapView.controller.setZoom(15.0)
            mapView.controller.setCenter(GeoPoint(lat.toDouble(), lon.toDouble()))
            
            // Then animate to the closer zoom
            mapView.postDelayed({
                mapView.controller.animateTo(
                    GeoPoint(lat.toDouble(), lon.toDouble()),
                    19.0,
                    2000L  // 2 seconds animation
                )
            }, 500)  // Wait 0.5 seconds before starting zoom
            
            // Add a marker for this specific landmark
            val marker = Marker(mapView).apply {
                position = GeoPoint(lat.toDouble(), lon.toDouble())
                title = args.getString("landmarkTitle", "")
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            }
            mapView.overlays.add(marker)
            marker.showInfoWindow()
        } else {
            // Fallback: center on the first landmark
            LandmarkData.landmarks.firstOrNull()?.let { landmark ->
                mapView.controller.setZoom(16.0)
                mapView.controller.setCenter(GeoPoint(landmark.latitude, landmark.longitude))
            }
        }
    }

    private fun setupLandmarkMarkers() {
        // Clear existing markers
        markers.forEach { mapView.overlays.remove(it) }
        markers.clear()

        // Add markers for all landmarks
        LandmarkData.landmarks.forEach { landmark ->
            val marker = Marker(mapView).apply {
                position = GeoPoint(landmark.latitude, landmark.longitude)
                title = landmark.name
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                setInfoWindow(CustomInfoWindow(mapView, landmark))
            }
            
            // Handle marker click
            marker.setOnMarkerClickListener { _, _ ->
                marker.showInfoWindow()
                true
            }
            
            markers.add(marker)
            mapView.overlays.add(marker)
        }
    }

    private inner class CustomInfoWindow(
        mapView: MapView,
        private val landmark: Landmark
    ) : InfoWindow(R.layout.marker_info_window, mapView) {

        override fun onOpen(item: Any?) {
            val view = mView
            val titleView = view.findViewById<TextView>(R.id.title)
            val snippetView = view.findViewById<TextView>(R.id.snippet)
            val detailsButton = view.findViewById<Button>(R.id.detailsButton)

            titleView.text = landmark.name
            val distance = calculateDistance(landmark.latitude, landmark.longitude)
            snippetView.text = if (distance != null) {
                formatDistance(distance)
            } else {
                "Distance unavailable"
            }

            detailsButton.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("landmarkTitle", landmark.name)
                    putString("landmarkDescription", landmark.description)
                    putInt("landmarkImageResId", landmark.imageResId)
                    putFloat("latitude", landmark.latitude.toFloat())
                    putFloat("longitude", landmark.longitude.toFloat())
                }
                findNavController().navigate(R.id.landmarkDetailFragment, bundle)
            }
        }

        override fun onClose() {
            // Nothing to do here
        }
    }

    private fun calculateDistance(latitude: Double, longitude: Double): Float? {
        if (currentLocation == null) return null
        
        val results = FloatArray(1)
        Location.distanceBetween(
            currentLocation!!.latitude,
            currentLocation!!.longitude,
            latitude,
            longitude,
            results
        )
        return results[0]
    }

    private fun formatDistance(distance: Float): String {
        val df = DecimalFormat("#.#")
        return if (distance < 1000) {
            "${df.format(distance)}m"
        } else {
            "${df.format(distance / 1000)}km"
        }
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    currentLocation = it
                    // Update all marker info windows with new distances
                    updateMarkerDistances()
                }
            }
        }
    }

    private fun updateMarkerDistances() {
        markers.forEach { marker ->
            val landmark = LandmarkData.landmarks.find { 
                it.latitude == marker.position.latitude && 
                it.longitude == marker.position.longitude 
            }
            landmark?.let {
                if (marker.isInfoWindowShown) {
                    marker.closeInfoWindow()
                    marker.showInfoWindow()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Location permission is required for distance calculation",
                    Toast.LENGTH_SHORT
                ).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 