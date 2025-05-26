package com.example.launcher.ui.landmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.launcher.R
import com.example.launcher.adapters.LandmarkImageAdapter
import com.example.launcher.databinding.FragmentLandmarkDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class LandmarkDetailFragment : Fragment() {
    private var _binding: FragmentLandmarkDetailBinding? = null
    private val binding get() = _binding!!
    private val args: LandmarkDetailFragmentArgs by navArgs()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    // Historical information for each landmark
    private val historicalInfo = mapOf(
        "Belfry Tower" to """
            The Dumaguete Belfry, also known as the Campanario de Dumaguete, is one of the city's most iconic landmarks. 
            Built in 1811 during the Spanish colonial period, it served as a watchtower to warn residents of approaching Moro pirates.
            
            Key Historical Points:
            • Constructed under the supervision of Spanish friars
            • Originally part of the St. Catherine of Alexandria Church
            • Used as a lookout point for pirate attacks
            • Survived multiple earthquakes and typhoons
            • Declared a National Historical Landmark in 2011
            
            The belfry stands as a testament to Dumaguete's rich colonial history and continues to be a symbol of the city's heritage.
        """.trimIndent(),
        
        "Silliman Hall" to """
            Silliman Hall is the oldest American structure in the Philippines and the first building of Silliman University.
            
            Historical Significance:
            • Built in 1903, just one year after the university's founding
            • Designed by American architect William Parsons
            • Originally served as the main academic building
            • Named after Dr. Horace Silliman, the university's first benefactor
            • Survived World War II and multiple natural disasters
            
            The building's neoclassical architecture reflects the American colonial influence and remains a centerpiece of the university campus.
        """.trimIndent()
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandmarkDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupImageGallery()
        setupLandmarkDetails()
        setupMapButton()
        loadWebData()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupImageGallery() {
        // Get multiple images for the landmark
        val images = when (args.landmarkTitle) {
            "Belfry Tower" -> listOf(
                R.drawable.img,  // Main image
                R.drawable.belfry_2,
                R.drawable.belfry_map,  // Map view
                 // Additional view (replace with actual image)
            )
            "Silliman Hall" -> listOf(
                R.drawable.img_1,  // Main image
                R.drawable.sillimanhall_2,
                R.drawable.sillimanhall_map  // Map view
                 // Additional view (replace with actual image)
            )
            else -> listOf(args.landmarkImageResId)
        }

        val imageAdapter = LandmarkImageAdapter(images)
        binding.imageViewPager.adapter = imageAdapter

        // Connect ViewPager2 with TabLayout for dots
        TabLayoutMediator(binding.imageTabLayout, binding.imageViewPager) { _, _ ->
            // We don't need to do anything here as we're using custom tab indicators
        }.attach()
    }

    private fun setupLandmarkDetails() {
        binding.landmarkTitle.text = args.landmarkTitle
        binding.landmarkDescription.text = args.landmarkDescription
        binding.landmarkLocation.text = "Latitude: ${args.latitude}, Longitude: ${args.longitude}"
        
        // Set historical information
        binding.landmarkHistory.text = historicalInfo[args.landmarkTitle] ?: "Historical information not available."
    }

    private fun setupMapButton() {
        binding.fabMap.setOnClickListener {
            // Navigate to the map fragment with the landmark details
            val action = LandmarkDetailFragmentDirections.actionDetailToMap(
                landmarkTitle = args.landmarkTitle,
                landmarkDescription = args.landmarkDescription,
                landmarkImageResId = args.landmarkImageResId,
                latitude = args.latitude,
                longitude = args.longitude
            )
            findNavController().navigate(action)
        }
    }

    private fun loadWebData() {
        binding.btnLoadMore.setOnClickListener {
            coroutineScope.launch {
                try {
                    // Show loading state
                    binding.btnLoadMore.isEnabled = false
                    binding.btnLoadMore.text = "Loading..."

                    // Fetch data from multiple sources
                    val data = withContext(Dispatchers.IO) {
                        val landmarkData = when (args.landmarkTitle) {
                            "Belfry Tower" -> """
                                Cultural Significance:
                                • A symbol of Dumaguete's Spanish colonial heritage
                                • Featured in numerous local festivals and celebrations
                                • Popular tourist attraction and photography spot
                                • Part of the city's historical walking tours
                                
                                Visitor Information:
                                • Open to public viewing 24/7
                                • Best viewed during sunset
                                • Located in the heart of Dumaguete City
                                • Accessible by public transportation
                                
                                Nearby Attractions:
                                • St. Catherine of Alexandria Church
                                • Quezon Park
                                • Rizal Boulevard
                                • Dumaguete City Public Market
                            """.trimIndent()
                            
                            "Silliman Hall" -> """
                                Architectural Features:
                                • Neoclassical design with American colonial influence
                                • Two-story structure with wide verandas
                                • Original wooden floors and staircases
                                • Historical markers and plaques
                                
                                Current Use:
                                • Houses the College of Arts and Sciences
                                • Contains historical archives and exhibits
                                • Venue for cultural events and lectures
                                • Administrative offices
                                
                                Visitor Guidelines:
                                • Open during university hours
                                • Guided tours available
                                • Photography allowed
                                • Respect academic activities
                            """.trimIndent()
                            
                            else -> "Additional information not available."
                        }

                        // Try to get Wikipedia data as supplementary information
                        try {
                            val query = args.landmarkTitle.replace(" ", "_")
                            val url = "https://en.wikipedia.org/api/rest_v1/page/summary/$query"
                            val response = URL(url).readText()
                            val jsonResponse = JSONObject(response)
                            val extract = jsonResponse.getString("extract")
                            val contentUrls = jsonResponse.optJSONObject("content_urls")
                            val desktopUrl = contentUrls?.optJSONObject("desktop")?.getString("page")
                            
                            """
                            $landmarkData
                            
                            Additional Information from Wikipedia:
                            $extract
                            
                            Read more on Wikipedia: $desktopUrl
                            """.trimIndent()
                        } catch (e: Exception) {
                            landmarkData
                        }
                    }

                    // Update UI with fetched data
                    binding.webDataContent.text = data
                    binding.btnLoadMore.text = "Refresh"
                } catch (e: Exception) {
                    binding.webDataContent.text = "Failed to load additional information. Please try again."
                } finally {
                    binding.btnLoadMore.isEnabled = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 