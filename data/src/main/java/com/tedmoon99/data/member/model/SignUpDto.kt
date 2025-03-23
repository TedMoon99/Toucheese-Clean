package com.tedmoon99.data.member.model


import com.google.gson.annotations.SerializedName

data class SignUpDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String
)