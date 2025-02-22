package com.tedmoon99.data.model.remote.member.token

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("refreshToken")
    val refreshToken: String?,
    @SerializedName("deviceId")
    val deviceId: String?
) {
}