package com.tedmoon99.domain.member.usecase

import com.tedmoon99.domain.kakao.model.KakaoSignInResultEntity
import com.tedmoon99.domain.member.model.SignInResult
import com.tedmoon99.domain.member.model.SignOutResult
import com.tedmoon99.domain.member.model.SignUpResult
import com.tedmoon99.domain.member.model.UpdateInfoResult
import kotlinx.coroutines.flow.StateFlow

interface MemberUseCase {

    suspend fun requestSignIn(email: String, password: String): SignInResult

    suspend fun requestKakaoSignIn(): KakaoSignInResultEntity

    suspend fun requestSignOut(): SignOutResult

    suspend fun requestUpdateUserInfo(name: String, phone: String): UpdateInfoResult

    suspend fun requestSignUp(email: String, password: String, name: String, phone: String): SignUpResult

    fun getSignOutState(): StateFlow<Boolean>
}