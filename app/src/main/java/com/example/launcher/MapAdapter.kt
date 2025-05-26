package com.example.launcher

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.launcher.model.Landmark
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow

class MapAdapter(
    private val context: Context,
    private val mapView: MapView,
    private val onMarkerClick: (Landmark) -> Unit
) {
    private val markers = mutableListOf<Marker>()

    fun addLandmarks(landmarks: List<Landmark>) {
        clearMarkers()
        landmarks.forEach { landmark ->
            val marker = Marker(mapView).apply {
                position = landmark.toGeoPoint()
                title = landmark.name
                snippet = landmark.description
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                setOnMarkerClickListener { _, _ ->
                    onMarkerClick(landmark)
                    true
                }
            }
            markers.add(marker)
            mapView.overlays.add(marker)
        }
        mapView.invalidate()
    }

    fun clearMarkers() {
        markers.forEach { mapView.overlays.remove(it) }
        markers.clear()
        mapView.invalidate()
    }
}
