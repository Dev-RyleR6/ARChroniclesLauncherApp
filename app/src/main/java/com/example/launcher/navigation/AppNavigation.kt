package com.example.launcher.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.launcher.ui.about.AboutScreen
import com.example.launcher.ui.home.HomeScreen
import com.example.launcher.ui.settings.SettingsScreen
import com.example.launcher.ui.common.Screen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Maps.route) {
            // MapsScreen()
        }
        composable(Screen.AR.route) {
            // ARScreen()
        }
        composable(Screen.Favorites.route) {
            // FavoritesScreen()
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onThemeChange = { /* Handle theme change */ },
                onNotificationsChange = { /* Handle notifications change */ },
                onLocationChange = { /* Handle location change */ },
                onClearCache = { /* Handle cache clearing */ },
                onAboutClick = { navController.navigate("about") },
                isDarkMode = false, // Get from ViewModel
                isNotificationsEnabled = true, // Get from ViewModel
                isLocationEnabled = true // Get from ViewModel
            )
        }
        composable("about") {
            AboutScreen(
                onNavigateBack = { navController.popBackStack() },
                onPrivacyPolicyClick = { /* Handle privacy policy click */ },
                onTermsOfServiceClick = { /* Handle terms of service click */ },
                onContactUsClick = { /* Handle contact us click */ }
            )
        }
    }
} 