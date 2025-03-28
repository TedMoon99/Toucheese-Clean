package com.tedmoon99.data.common.token.model

data class TokenApiResponse<T>(
    val success: Boolean,
    val payload: Any?,
    val error: Error?,
    val data: T?
)
