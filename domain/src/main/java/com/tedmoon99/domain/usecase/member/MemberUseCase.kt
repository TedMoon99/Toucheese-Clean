package com.tedmoon99.domain.usecase.member

import com.tedmoon99.domain.entity.remote.member.KakaoSignInResultEntity
import com.tedmoon99.domain.intent.member.SignInResult
import com.tedmoon99.domain.intent.member.SignOutResult
import com.tedmoon99.domain.intent.member.UpdateInfoResult
import kotlinx.coroutines.flow.StateFlow

interface MemberUseCase {

    suspend fun requestSignIn(email: String, password: String): SignInResult

    suspend fun requestKakaoSignIn(): KakaoSignInResultEntity

    suspend fun requestSignOut(): SignOutResult

    suspend fun requestUpdateUserInfo(name: String, phone: String): UpdateInfoResult

    fun getSignOutState(): StateFlow<Boolean>
}