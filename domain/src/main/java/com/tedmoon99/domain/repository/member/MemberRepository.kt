package com.tedmoon99.domain.repository.member

import com.tedmoon99.domain.entity.auth.sign_in.SignInRequestEntity
import com.tedmoon99.domain.intent.member.SignInResult

interface MemberRepository {

    suspend fun requestSignIn(request: SignInRequestEntity): SignInResult

    suspend fun getUserId(): Int?

    suspend fun getUserEmail(): String?

    suspend fun getUserName(): String?
}