package com.example.launcher.ui.landmarks

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.launcher.LandmarkAdapter
import com.example.launcher.R
import com.example.launcher.model.LandmarkData
import com.example.launcher.databinding.FragmentLandmarkListBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LandmarkListFragment : Fragment() {
    private var _binding: FragmentLandmarkListBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var adapter: LandmarkAdapter
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandmarkListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        setupRecyclerView()
        setupMapButton()
        
        if (checkLocationPermission()) {
            getCurrentLocation()
        } else {
            requestLocationPermission()
        }
    }

    private fun setupRecyclerView() {
        adapter = LandmarkAdapter(LandmarkData.landmarks) { landmark ->
            // Navigate to LandmarkDetailFragment using Safe Args
            val action = LandmarkListFragmentDirections.actionLandmarkListToDetail(
                landmarkTitle = landmark.name,
                landmarkDescription = landmark.description,
                landmarkImageResId = landmark.imageResId,
                latitude = landmark.latitude.toFloat(),
                longitude = landmark.longitude.toFloat()
            )
            findNavController().navigate(action)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@LandmarkListFragment.adapter
        }
    }

    private fun setupMapButton() {
        binding.fabMap.setOnClickListener {
            findNavController().navigate(R.id.navigation_map)
        }
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    adapter.updateLocation(it.latitude, it.longitude)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Location permission is required for distance calculation",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 