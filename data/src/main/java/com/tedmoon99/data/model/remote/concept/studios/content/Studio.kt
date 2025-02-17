package com.tedmoon99.data.model.remote.concept.studios.content

import com.google.gson.annotations.SerializedName

data class Studio(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileImage")
    val profileImage: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("imageUrls")
    val imageUrls: List<String>
)