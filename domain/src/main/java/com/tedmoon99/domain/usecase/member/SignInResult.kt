package com.tedmoon99.domain.usecase.member

sealed class SignInResult {
    data class Success(val success: String): SignInResult()
    data class Failure(val error: String): SignInResult()
    data object NetworkError: SignInResult()
}