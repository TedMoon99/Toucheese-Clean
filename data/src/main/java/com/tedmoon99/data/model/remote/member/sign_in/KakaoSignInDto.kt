package com.tedmoon99.data.model.remote.member.sign_in

data class KakaoSignInDto(
    val idToken: String,
    val accessToken: String,
    val platform: String,
    val deviceId: String?,
)
