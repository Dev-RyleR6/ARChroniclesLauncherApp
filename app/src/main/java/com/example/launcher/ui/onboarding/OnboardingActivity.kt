package com.example.launcher.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.launcher.MainActivity
import com.example.launcher.R
import com.example.launcher.databinding.ActivityOnboardingBinding
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var onboardingAdapter: OnboardingAdapter

    private val onboardingItems = listOf(
        OnboardingItem(
            R.drawable.ar,
            "Experience History in AR",
            "Discover your surroundings like never before with our Augmented Reality Landmark Detection app! Simply point your camera toward nearby landmarks, and let our app reveal fascinating information, image models, and historical insights—right on your screen."
        ),
        OnboardingItem(
            R.drawable.landm,
            "Landmark Info on the Spot",
            "Get instant access to historical facts, cultural insights, and fun trivia about each landmark you encounter."
        ),
        OnboardingItem(
            R.drawable.mp,
            "Interactive Map View",
            "Explore your surroundings and locate nearby landmarks using a dynamic, easy-to-use map."
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        setupTabLayout()
        setupButton()
    }

    private fun setupViewPager() {
        viewPager = binding.viewPager
        onboardingAdapter = OnboardingAdapter(onboardingItems)
        viewPager.adapter = onboardingAdapter
        
        // Enable swiping
        viewPager.isUserInputEnabled = true
        
        // Update button text based on page
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateButtonText(position)
            }
        })
    }

    private fun setupTabLayout() {
        TabLayoutMediator(binding.tabLayout, viewPager) { _, _ -> }.attach()
    }

    private fun setupButton() {
        binding.buttonNext.setOnClickListener {
            if (viewPager.currentItem + 1 < onboardingAdapter.itemCount) {
                viewPager.currentItem += 1
            } else {
                startMainActivity()
            }
        }
    }

    private fun updateButtonText(position: Int) {
        binding.buttonNext.text = if (position == onboardingAdapter.itemCount - 1) {
            "Get Started"
        } else {
            "Next"
        }
    }

    private fun startMainActivity() {
        // Mark onboarding as shown
        getSharedPreferences("app_prefs", MODE_PRIVATE)
            .edit()
            .putBoolean("onboarding_shown", true)
            .apply()

        // Start MainActivity and clear back stack
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        // Prevent going back from onboarding
        if (viewPager.currentItem > 0) {
            viewPager.currentItem -= 1
        } else {
            super.onBackPressed()
        }
    }
} 