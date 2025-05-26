package com.example.launcher.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.launcher.LandmarkAdapter
import com.example.launcher.R
import com.example.launcher.model.Landmark

class LandmarksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmarks)

        val landmarks = listOf(
            Landmark(
                id = "1",
                name = "Belfry Tower",
                description = "A historical tower in Dumaguete.",
                latitude = 9.30498469076782,
                longitude = 123.30762079554815,
                imageResId = R.drawable.img
            ),
            Landmark(
                id = "2",
                name = "Silliman Hall",
                description = "Silliman Hall is a historic building within the Silliman University campus in Dumaguete City.",
                latitude = 9.310958203023446,
                longitude = 123.30866402519013,
                imageResId = R.drawable.img_1
            )
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewLandmarkCards)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = LandmarkAdapter(landmarks) { landmark ->
            val intent = Intent(this, LandmarkDetailActivity::class.java).apply {
                putExtra("landmark_name", landmark.name)
                putExtra("landmark_description", landmark.description)
                putExtra("landmark_photo_res", landmark.imageResId)
            }
            startActivity(intent)
        }
    }
}
