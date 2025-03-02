package com.tedmoon99.domain.entity.remote.studio

data class StudioEntity(
    val id: Int,
    val name: String,
    val profileImage: String,
    val rating: Double,
    val price: Int,
    val imageUrls: List<String>
)
