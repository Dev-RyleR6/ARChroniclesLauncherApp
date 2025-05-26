package com.example.launcher.model

import com.example.launcher.model.Landmark
import com.example.launcher.R

object LandmarkData {
    val landmarks = listOf(
        Landmark(
            id = "1",
            name = "Belfry Tower",
            description = "The Dumaguete Belfry is one of the city's best known landmarks and is thought to be one of the oldest surviving structures in the city. Built in 1811, it served as a watchtower to warn against potential Moro pirate attacks.",
            latitude = 9.30498469076782,
            longitude = 123.30762079554815,
            imageResId = R.drawable.img
        ),
        Landmark(
            id = "2",
            name = "Silliman Hall",
            description = "Silliman Hall is a historic building within the Silliman University campus in Dumaguete City. Built in 1903, it is the oldest American structure in the Philippines and serves as the university's main administrative building.",
            latitude = 9.310958203023446,
            longitude = 123.30866402519013,
            imageResId = R.drawable.img_1
        ),
        // Add more landmarks here following the same pattern:
        // Landmark(
        //     id = "unique_id",
        //     name = "Your Landmark Name",
        //     description = "Your detailed description here",
        //     latitude = your_latitude,
        //     longitude = your_longitude,
        //     imageResId = R.drawable.your_image
        // )
    )
}
