package com.tedmoon99.domain.intent.member

sealed class SignOutResult{
    data class Success(val message: String): SignOutResult()
    data class Failure(val message: String): SignOutResult()
}
