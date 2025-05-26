package com.example.launcher.model

import org.osmdroid.util.GeoPoint
import kotlin.math.roundToInt

data class Landmark(
    val id: String,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val imageResId: Int = 0
) {

    // Helper function to get GeoPoint
    fun toGeoPoint(): GeoPoint = GeoPoint(latitude, longitude)

    // Calculate distance from current location to landmark
    fun calculateDistance(currentLat: Double, currentLon: Double): String {
        val distance = calculateDistance(latitude, longitude, currentLat, currentLon)
        return when {
            distance < 1 -> "${(distance * 1000).toInt()}m"
            else -> String.format("%.1fkm", distance)
        }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371.0 // Earth's radius in kilometers
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return r * c
    }
} 