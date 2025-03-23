package com.tedmoon99.data.common.token.model

import com.google.gson.annotations.SerializedName

data class RefreshTokenDto(
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("deviceId")
    val deviceId: String
) {
    override fun toString(): String {
        return "refreshToken: $refreshToken\n" +
                "deviceId: $deviceId"
    }
}