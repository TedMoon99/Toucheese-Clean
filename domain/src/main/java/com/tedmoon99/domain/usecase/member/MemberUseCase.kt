package com.tedmoon99.domain.usecase.member

import com.tedmoon99.domain.intent.member.SignInResult
import com.tedmoon99.domain.intent.member.SignOutResult
import kotlinx.coroutines.flow.StateFlow

interface MemberUseCase {

    suspend fun requestSignIn(email: String, password: String): SignInResult

    suspend fun requestSignOut(): SignOutResult

    fun getSignOutState(): StateFlow<Boolean>
}