package com.tedmoon99.data.studio.model

import com.google.gson.annotations.SerializedName

data class Studio(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileImage")
    val profileImage: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("price")
    val price: Int,
    @SerializedName("imageUrls")
    val imageUrls: List<String>
)