package com.tedmoon99.domain.repository.member

import com.tedmoon99.domain.entity.remote.member.AdditionalInfoEntity
import com.tedmoon99.domain.entity.remote.member.SignInRequestEntity
import com.tedmoon99.domain.intent.member.SignInResult
import com.tedmoon99.domain.intent.member.SignOutResult
import com.tedmoon99.domain.intent.member.UpdateInfoResult

interface MemberRepository {

    suspend fun requestSignIn(request: SignInRequestEntity): SignInResult

    suspend fun requestSignOut(): SignOutResult

    suspend fun requestUpdateUserInfo(request: AdditionalInfoEntity): UpdateInfoResult

    suspend fun setUserId(userId: Int)

    suspend fun getUserId(): Int?

    suspend fun setUserEmail(email: String)

    suspend fun getUserEmail(): String?

    suspend fun setUserName(userName: String)

    suspend fun getUserName(): String?
}