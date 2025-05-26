package com.example.launcher.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.launcher.R
import android.widget.ImageView
import android.widget.TextView

class LandmarkDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmark_detail)

        val imageView = findViewById<ImageView>(R.id.detailLandmarkImage)
        val nameView = findViewById<TextView>(R.id.detailLandmarkName)
        val descView = findViewById<TextView>(R.id.detailLandmarkDescription)

        val title = intent.getStringExtra("landmark_title") ?: ""
        val description = intent.getStringExtra("landmark_description") ?: ""
        val imageResId = intent.getIntExtra("landmark_image_res", R.drawable.img)
        val latitude = intent.getDoubleExtra("landmark_latitude", 0.0)
        val longitude = intent.getDoubleExtra("landmark_longitude", 0.0)

        nameView.text = title
        descView.text = description
        imageView.setImageResource(imageResId)
    }
}
