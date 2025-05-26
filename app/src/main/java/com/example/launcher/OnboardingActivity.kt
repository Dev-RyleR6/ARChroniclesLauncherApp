package com.example.launcher

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.launcher.databinding.ActivityOnboardingBinding
import com.example.launcher.ui.onboarding.OnboardingAdapter
import com.example.launcher.ui.onboarding.OnboardingItem
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var viewPager: ViewPager2

    private val onboardingItems = listOf(
        OnboardingItem(
            R.drawable.ar,
            "Welcome to ARChronicles",
            "Discover your surroundings like never before with our Augmented Reality Landmark Detection app! Simply point your camera toward nearby landmarks, and let our app reveal fascinating information, image models, and historical insights—right on your screen."
        ),
        OnboardingItem(
            R.drawable.landm,
            "Popular Categories",
            "Explore our curated collections of landmarks:\n\n" +
            "• Historical Monuments\n" +
            "• Cultural Heritage Sites\n" +
            "• Natural Wonders\n" +
            "• Architectural Marvels\n" +
            "• Local Hidden Gems"
        ),
        OnboardingItem(
            R.drawable.mp,
            "Interactive Map Features",
            "Our advanced map features help you discover and navigate:\n\n"

        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if onboarding has been shown
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        if (prefs.getBoolean("onboarding_shown", false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        // Show splash screen first
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            startOnboarding()
        }, 1500) // Show splash for 1.5 seconds
    }

    private fun startOnboarding() {
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager
        val adapter = OnboardingAdapter(onboardingItems)
        viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, viewPager) { _, _ -> }.attach()

        binding.buttonNext.setOnClickListener {
            if (viewPager.currentItem + 1 < onboardingItems.size) {
                viewPager.currentItem += 1
            } else {
                // Save that onboarding has been shown
                getSharedPreferences("app_prefs", MODE_PRIVATE)
                    .edit()
                    .putBoolean("onboarding_shown", true)
                    .apply()

                // Launch main activity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.buttonNext.text = if (position == onboardingItems.size - 1) "Get Started" else "Next"
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::viewPager.isInitialized) {
            viewPager.unregisterOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {})
        }
    }
} 