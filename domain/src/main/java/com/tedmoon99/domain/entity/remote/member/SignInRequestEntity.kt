package com.tedmoon99.domain.entity.remote.member

data class SignInRequestEntity(
    val email: String,
    val password: String,
    val deviceId: String? = null,
)
