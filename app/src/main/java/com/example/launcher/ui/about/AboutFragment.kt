package com.example.launcher.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.launcher.R
import com.example.launcher.databinding.FragmentAboutBinding
import com.google.android.material.card.MaterialCardView

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDeveloperInfo()
        setupVersionInfo()
        setupLinks()
        setupDeveloperProfiles()
    }

    private fun setupDeveloperInfo() {
        binding.textViewDeveloperName.text = "ARChronicles Team"
        binding.textViewDeveloperDescription.text = "We are passionate about bringing history to life through augmented reality and interactive maps. Our mission is to make cultural heritage more accessible and engaging for everyone."
    }

    private fun setupVersionInfo() {
        binding.textViewVersion.text = "Version 1.0.0"
        binding.textViewCopyright.text = "© 2024 ARChronicles. All rights reserved."
    }

    private fun setupDeveloperProfiles() {
        // Developer 1
        binding.textViewDeveloper1Name.text = "Ryle Anthony Gabotero"
        binding.textViewDeveloper1Position.text = "Lead Developer"
        binding.imageViewDeveloper1.setImageResource(R.drawable.developerr6)

        // Developer 2
        binding.textViewDeveloper2Name.text = "John Doe"
        binding.textViewDeveloper2Position.text = "UI/UX Designer"
        binding.imageViewDeveloper2.setImageResource(R.drawable.profile_john)
    }

    private fun setupLinks() {
        // GitHub link
        binding.cardGitHub.setOnClickListener {
            openUrl("https://github.com/Dev-RyleR6")
        }

        // Website link
        binding.cardWebsite.setOnClickListener {
            openUrl("https://archronicles.com")
        }

        // Email link
        binding.cardEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("ryleanthony.gabotero@foundationu.com")
            }
            startActivity(intent)
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 