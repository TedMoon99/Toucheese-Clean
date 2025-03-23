package com.tedmoon99.domain.member.model

data class SignInRequestEntity(
    val email: String,
    val password: String,
    val deviceId: String? = null,
)
