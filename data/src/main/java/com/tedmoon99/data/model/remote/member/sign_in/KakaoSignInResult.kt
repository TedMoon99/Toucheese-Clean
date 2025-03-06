package com.tedmoon99.data.model.remote.member.sign_in

data class KakaoSignInResult(
    val success: Boolean,
    val isFirstLogin: Boolean = false
)
