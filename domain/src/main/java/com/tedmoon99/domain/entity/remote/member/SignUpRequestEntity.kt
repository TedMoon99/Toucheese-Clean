package com.tedmoon99.domain.entity.remote.member

data class SignUpRequestEntity(
    val email: String,
    val password: String,
    val name: String,
    val phone: String
)