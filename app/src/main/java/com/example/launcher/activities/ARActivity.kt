package com.example.launcher.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ARActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launchUnityARApp()
        // Optionally finish this activity so it doesn’t stay in the back stack
        finish()
    }

    private fun launchUnityARApp() {
        try {
            val intent = Intent().apply {
                setClassName(
                    "com.unity.template.ar_mobile",
                    "com.unity3d.player.UnityPlayerGameActivity"
                )
            }
            startActivity(intent)
        } catch (e: Exception) {
            // Fallback to Play Store if Unity app is not installed
            try {
                val marketIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("market://details?id=com.unity.template.ar_mobile")
                    setPackage("com.android.vending")
                }
                startActivity(marketIntent)
            } catch (ex: Exception) {
                Toast.makeText(
                    this,
                    "AR app not installed. Please install it manually.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
