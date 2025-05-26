package com.example.launcher.ui.about

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    fun openPrivacyPolicy() {
        // Replace with your privacy policy URL
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://your-privacy-policy-url.com"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        // You'll need to handle this in your activity/fragment
    }

    fun openTermsOfService() {
        // Replace with your terms of service URL
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://your-terms-of-service-url.com"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        // You'll need to handle this in your activity/fragment
    }

    fun openContactUs() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("ryleanthony.gabotero@foundationu.com")
            putExtra(Intent.EXTRA_SUBJECT, "Archronicles App Feedback")
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        // You'll need to handle this in your activity/fragment
    }
} 