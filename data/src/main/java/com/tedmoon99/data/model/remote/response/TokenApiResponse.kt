package com.tedmoon99.data.model.remote.response

data class TokenApiResponse<T>(
    val success: Boolean,
    val payload: Any?,
    val error: Error?,
    val data: T?
)
