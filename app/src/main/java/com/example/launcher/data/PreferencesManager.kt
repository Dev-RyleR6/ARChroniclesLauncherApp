package com.example.launcher.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesManager(private val context: Context) {

    companion object {
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
        private val MAP_TYPE = stringPreferencesKey("map_type")
        private val SEARCH_RADIUS = intPreferencesKey("search_radius")
        private val LAST_LATITUDE = stringPreferencesKey("last_latitude")
        private val LAST_LONGITUDE = stringPreferencesKey("last_longitude")
    }

    // Dark mode preference
    val darkModeFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_MODE] ?: false
        }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE] = enabled
        }
    }

    // Map type preference
    val mapTypeFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[MAP_TYPE] ?: "standard"
        }

    suspend fun setMapType(type: String) {
        context.dataStore.edit { preferences ->
            preferences[MAP_TYPE] = type
        }
    }

    // Search radius preference
    val searchRadiusFlow: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[SEARCH_RADIUS] ?: 1000
        }

    suspend fun setSearchRadius(radius: Int) {
        context.dataStore.edit { preferences ->
            preferences[SEARCH_RADIUS] = radius
        }
    }

    // Last known location preferences
    val lastLocationFlow: Flow<Pair<Double?, Double?>> = context.dataStore.data
        .map { preferences ->
            val lat = preferences[LAST_LATITUDE]?.toDoubleOrNull()
            val lng = preferences[LAST_LONGITUDE]?.toDoubleOrNull()
            Pair(lat, lng)
        }

    suspend fun setLastLocation(latitude: Double, longitude: Double) {
        context.dataStore.edit { preferences ->
            preferences[LAST_LATITUDE] = latitude.toString()
            preferences[LAST_LONGITUDE] = longitude.toString()
        }
    }
} 