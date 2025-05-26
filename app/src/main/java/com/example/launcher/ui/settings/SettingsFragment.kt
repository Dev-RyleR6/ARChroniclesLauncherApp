package com.example.launcher.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.Preference
import androidx.preference.SwitchPreferenceCompat
import androidx.preference.ListPreference
import androidx.lifecycle.lifecycleScope
import com.example.launcher.R
import com.example.launcher.data.PreferencesManager
import com.example.launcher.databinding.DialogAboutBinding
import kotlinx.coroutines.launch

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesManager = PreferencesManager(requireContext())
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        
        // Theme preference
        findPreference<ListPreference>("theme")?.setOnPreferenceChangeListener { _, newValue ->
            lifecycleScope.launch {
                when (newValue) {
                    "light" -> {
                        preferencesManager.setDarkMode(false)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    "dark" -> {
                        preferencesManager.setDarkMode(true)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    "system" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                }
            }
            true
        }
        
        // Language preference
        findPreference<ListPreference>("language")?.setOnPreferenceChangeListener { _, _ ->
            Toast.makeText(context, "Language change requires app restart", Toast.LENGTH_LONG).show()
            true
        }
        
        // About preference
        findPreference<Preference>("about")?.setOnPreferenceClickListener {
            showAboutDialog()
            true
        }
        
        // Version preference
        findPreference<Preference>("version")?.apply {
            summary = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0).versionName
        }
        
        // Data usage preference
        findPreference<SwitchPreferenceCompat>("data_usage")?.setOnPreferenceChangeListener { _, newValue ->
            Toast.makeText(context, 
                if (newValue as Boolean) "Data usage enabled" else "Data usage disabled",
                Toast.LENGTH_SHORT).show()
            true
        }

        // Notifications preference
        findPreference<SwitchPreferenceCompat>("notifications")?.setOnPreferenceChangeListener { _, newValue ->
            Toast.makeText(context,
                if (newValue as Boolean) "Notifications enabled" else "Notifications disabled",
                Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun showAboutDialog() {
        val dialogBinding = DialogAboutBinding.inflate(LayoutInflater.from(requireContext()))
        
        // Set developer information
        dialogBinding.textViewDeveloperName.text = "ARChronicles"
        dialogBinding.textViewDeveloperDescription.text = "We are passionate about bringing history to life through augmented reality and interactive maps. Our mission is to make cultural heritage more accessible and engaging for everyone."
        
        // Set developer profiles
        dialogBinding.textViewDeveloper1Name.text = "Ryle Anthony Gabotero"
        dialogBinding.textViewDeveloper1Position.text = "Lead Developer"
        dialogBinding.imageViewDeveloper1.setImageResource(R.drawable.developerr6)
        
        dialogBinding.textViewDeveloper2Name.text = "Neil Allen Faburada"
        dialogBinding.textViewDeveloper2Position.text = "UI/UX Designer"
        dialogBinding.imageViewDeveloper2.setImageResource(R.drawable.neil)

        dialogBinding.textViewDeveloper3Name.text = "Janzen Jay Jumawan"
        dialogBinding.textViewDeveloper3Position.text = "UI/UX Designer"
        dialogBinding.imageViewDeveloper3.setImageResource(R.drawable.janzen)
        
        // Set version and copyright
        dialogBinding.textViewVersion.text = "Version ${requireContext().packageManager.getPackageInfo(requireContext().packageName, 0).versionName}"
        dialogBinding.textViewCopyright.text = "© 2024 ARChronicles. All rights reserved."
        
        // Setup click listeners for links
        dialogBinding.cardGitHub.setOnClickListener {
            openUrl("https://github.com/Dev-RyleR6")
        }
        
        dialogBinding.cardWebsite.setOnClickListener {
            openUrl("https://archronicles.com")
        }
        
        dialogBinding.cardEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:ryleanthony.gabotero@foundationu.com")
            }
            startActivity(intent)
        }
        
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
} 