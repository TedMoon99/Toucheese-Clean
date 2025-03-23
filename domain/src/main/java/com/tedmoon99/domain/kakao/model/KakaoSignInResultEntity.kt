package com.tedmoon99.domain.kakao.model

data class KakaoSignInResultEntity(
    val success: Boolean,
    val isFirstLogin: Boolean = false
)
