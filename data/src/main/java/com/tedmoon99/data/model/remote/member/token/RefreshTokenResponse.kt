package com.tedmoon99.data.model.remote.member.token


import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(
    @SerializedName("deviceId")
    val deviceId: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)