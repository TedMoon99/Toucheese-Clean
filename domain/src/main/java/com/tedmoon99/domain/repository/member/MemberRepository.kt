package com.tedmoon99.domain.repository.member

import com.tedmoon99.domain.entity.remote.member.SignInRequestEntity
import com.tedmoon99.domain.intent.member.SignInResult
import com.tedmoon99.domain.intent.member.SignOutResult

interface MemberRepository {

    suspend fun requestSignIn(request: SignInRequestEntity): SignInResult

    suspend fun requestSignOut(): SignOutResult

    suspend fun getUserId(): Int?

    suspend fun getUserEmail(): String?

    suspend fun getUserName(): String?
}