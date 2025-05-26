package com.example.launcher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.launcher.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.NavController
import com.example.launcher.ui.onboarding.OnboardingActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if launched from Unity
        val fromUnity = intent.getBooleanExtra("fromUnity", false)
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val onboardingShown = prefs.getBoolean("onboarding_shown", false)

        if (!onboardingShown && !fromUnity) {
            // Launch the onboarding activity if onboarding hasn't been shown and NOT coming from Unity
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
            return
        }

        // Normal setup when onboarding is already shown or when coming from Unity
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar
        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set up the bottom navigation
        val navView: BottomNavigationView = binding.navView
        navView.setupWithNavController(navController)

        // Prevent recreation of fragments
        navView.setOnItemReselectedListener { /* Do nothing */ }

        // Set up the ActionBar
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_landmarks,
                R.id.navigation_map,
                R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Handle Unity return if needed
        if (fromUnity) {
            handleUnityReturn()
        }
    }

    private fun handleUnityReturn() {
        // You can add specific navigation logic here if needed
        // For example, to navigate to a specific screen:
        // navController.navigate(R.id.navigation_home)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // Handle if the activity is already running and receives a new intent
        if (intent?.getBooleanExtra("fromUnity", false) == true) {
            handleUnityReturn()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
