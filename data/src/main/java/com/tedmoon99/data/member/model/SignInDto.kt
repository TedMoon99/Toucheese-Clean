package com.tedmoon99.data.member.model

data class SignInDto(
    val email: String,
    val password: String,
    val deviceId: String?,
)
