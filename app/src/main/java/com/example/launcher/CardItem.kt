package com.example.launcher

data class CardItem(
    val id: String = "",
    val name: String,
    val description: String,
    val iconResId: Int,
    val onClick: () -> Unit = {}
)
