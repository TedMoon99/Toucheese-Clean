package com.tedmoon99.domain.member.model

data class SignUpRequestEntity(
    val email: String,
    val password: String,
    val name: String,
    val phone: String
)