package com.tedmoon99.data.kakao.model

import com.google.gson.annotations.SerializedName

data class KakaoSignIn(
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("isFirstLogin")
    val isFirstLogin: Boolean,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("deviceId")
    val deviceId: String,
)
