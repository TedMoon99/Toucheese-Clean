package com.tedmoon99.domain.entity.auth.sign_in

data class SignInRequestEntity(
    val email: String,
    val password: String,
    val deviceId: String? = null,
)
