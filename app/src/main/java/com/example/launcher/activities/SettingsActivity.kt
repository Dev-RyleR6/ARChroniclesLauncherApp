package com.example.launcher.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.Preference
import androidx.preference.SwitchPreferenceCompat
import androidx.preference.ListPreference
import com.caverock.androidsvg.BuildConfig
import com.example.launcher.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment())
            .commit()
            
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.settings)
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    
    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
            
            // Theme preference
            findPreference<ListPreference>("theme")?.setOnPreferenceChangeListener { _, newValue ->
                when (newValue) {
                    "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    "system" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
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
                summary = BuildConfig.VERSION_NAME
            }
            
            // Data usage preference
            findPreference<SwitchPreferenceCompat>("data_usage")?.setOnPreferenceChangeListener { _, newValue ->
                Toast.makeText(context, 
                    if (newValue as Boolean) "Data usage enabled" else "Data usage disabled",
                    Toast.LENGTH_SHORT).show()
                true
            }
        }
        
        private fun showAboutDialog() {
            androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle(R.string.about)
                .setMessage(R.string.about_message)
                .setPositiveButton(android.R.string.ok, null)
                .show()
        }
    }
} 