package com.tedmoon99.domain.entity.remote.concept.studios

data class StudioEntity(
    val id: Int,
    val name: String,
    val profileImage: String,
    val rating: Int,
    val price: Int,
    val imageUrls: List<String>
)
