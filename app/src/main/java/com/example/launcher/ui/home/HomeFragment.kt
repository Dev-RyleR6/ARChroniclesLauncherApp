package com.example.launcher.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.launcher.CardItem
import com.example.launcher.CardItemAdapter
import com.example.launcher.R
import com.example.launcher.activities.ARActivity
import com.example.launcher.activities.LandmarkMapCardsActivity
import com.example.launcher.model.LandmarkData
import com.example.launcher.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var cardAdapter: CardItemAdapter
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var backCallback: OnBackPressedCallback? = null
    private var isUserScrolling = false
    private var isInitialized = false
    private var pageChangeCallback: ViewPager2.OnPageChangeCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (::viewPager.isInitialized && viewPager.currentItem > 0) {
                    viewPager.currentItem = viewPager.currentItem - 1
                } else {
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backCallback!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewPager = binding.viewPager
        tabLayout = binding.tabLayout
        
        // Initialize adapter
        cardAdapter = CardItemAdapter(emptyList())
        
        // Setup ViewPager2
        viewPager.adapter = cardAdapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.offscreenPageLimit = 1
        viewPager.isUserInputEnabled = true
        
        // Add page change callback to track user scrolling
        pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                isUserScrolling = state == ViewPager2.SCROLL_STATE_DRAGGING
            }
        }
        viewPager.registerOnPageChangeCallback(pageChangeCallback!!)
        
        setupCards()
    }

    private fun setupCards() {
        val cards = listOf(
            CardItem(
                name = "Welcome to ARChronicles",
                description = "Discover historical landmarks through augmented reality",
                iconResId = R.drawable.ic_ar
            ),
            CardItem(
                name = "Featured Landmarks",
                description = "Explore our curated collection of historical sites",
                iconResId = R.drawable.ic_landmark
            ),
            CardItem(
                name = "Interactive Map",
                description = "View landmarks on an interactive map",
                iconResId = R.drawable.ic_map
            )
        )

        cardAdapter = CardItemAdapter(cards) { card ->
            when (card.name) {
                "Welcome to ARChronicles" -> {
                    val intent = Intent(requireContext(), ARActivity::class.java)
                    startActivity(intent)
                }
                "Featured Landmarks" -> findNavController().navigate(R.id.navigation_landmarks)
                "Interactive Map" -> findNavController().navigate(R.id.action_home_to_map)
            }
        }
        viewPager.adapter = cardAdapter

        // Set up TabLayout after adapter is set
        setupTabLayout()
        
        // Mark as initialized after setup is complete
        isInitialized = true
    }

    private fun setupTabLayout() {
        // Create tabs for each card
        for (i in 0 until cardAdapter.itemCount) {
            tabLayout.addTab(tabLayout.newTab())
        }

        // Connect TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, _ ->
            // Empty tab configuration since we're using custom tab indicators
        }.attach()

        // Handle tab selection
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { position ->
                    viewPager.setCurrentItem(position, true)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {
                if (isInitialized && !isUserScrolling) {
                    tab?.position?.let { position ->
                        cardAdapter.items[position].onClick()
                    }
                }
            }
        })

        // Simple page transformer
        viewPager.setPageTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.15f
            page.alpha = 0.5f + r * 0.5f
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        backCallback?.remove()
        backCallback = null
        pageChangeCallback?.let { viewPager.unregisterOnPageChangeCallback(it) }
        pageChangeCallback = null
        _binding = null
    }
} 