package com.example.launcher.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.launcher.data.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    application: Application,
    private val userPreferences: UserPreferences
) : AndroidViewModel(application) {

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _isNotificationsEnabled = MutableStateFlow(false)
    val isNotificationsEnabled: StateFlow<Boolean> = _isNotificationsEnabled.asStateFlow()

    private val _isLocationEnabled = MutableStateFlow(false)
    val isLocationEnabled: StateFlow<Boolean> = _isLocationEnabled.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferences.isDarkMode.collect { isDark ->
                _isDarkMode.value = isDark
            }
        }
        viewModelScope.launch {
            userPreferences.isNotificationsEnabled.collect { isEnabled ->
                _isNotificationsEnabled.value = isEnabled
            }
        }
        viewModelScope.launch {
            userPreferences.isLocationEnabled.collect { isEnabled ->
                _isLocationEnabled.value = isEnabled
            }
        }
    }

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            userPreferences.setDarkMode(enabled)
        }
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            userPreferences.setNotificationsEnabled(enabled)
        }
    }

    fun setLocationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            userPreferences.setLocationEnabled(enabled)
        }
    }

    fun clearCache() {
        viewModelScope.launch {
            // Clear app cache
            getApplication<Application>().cacheDir.deleteRecursively()
        }
    }

    companion object {
        fun provideFactory(
            application: Application,
            userPreferences: UserPreferences
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return SettingsViewModel(application, userPreferences) as T
            }
        }
    }
} 