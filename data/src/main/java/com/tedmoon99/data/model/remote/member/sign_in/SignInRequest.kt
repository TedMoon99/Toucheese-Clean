package com.tedmoon99.data.model.remote.member.sign_in

data class SignInRequest(
    val email: String,
    val password: String,
    val deviceId: String?,
)
