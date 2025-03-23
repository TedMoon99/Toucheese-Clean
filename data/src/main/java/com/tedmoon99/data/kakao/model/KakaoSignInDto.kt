package com.tedmoon99.data.kakao.model

data class KakaoSignInDto(
    val idToken: String,
    val accessToken: String,
    val platform: String,
    val deviceId: String?,
)
