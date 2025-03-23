package com.tedmoon99.domain.studio.model

data class StudioEntity(
    val id: Int,
    val name: String,
    val profileImage: String,
    val rating: Double,
    val price: Int,
    val imageUrls: List<String>
)
