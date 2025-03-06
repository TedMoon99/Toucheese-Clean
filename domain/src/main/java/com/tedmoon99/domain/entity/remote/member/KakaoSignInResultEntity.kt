package com.tedmoon99.domain.entity.remote.member

data class KakaoSignInResultEntity(
    val success: Boolean,
    val isFirstLogin: Boolean = false
)
