package com.tedmoon99.data.model.remote.member.sign_in

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("deviceId")
    val deviceId: String?,
)
