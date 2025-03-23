package com.tedmoon99.domain.member.repository

import com.tedmoon99.domain.member.model.AdditionalInfoEntity
import com.tedmoon99.domain.member.model.SignInRequestEntity
import com.tedmoon99.domain.member.model.SignUpRequestEntity
import com.tedmoon99.domain.member.model.SignInResult
import com.tedmoon99.domain.member.model.SignOutResult
import com.tedmoon99.domain.member.model.SignUpResult
import com.tedmoon99.domain.member.model.UpdateInfoResult

interface MemberRepository {

    suspend fun requestSignIn(request: SignInRequestEntity): SignInResult

    suspend fun requestSignUp(request: SignUpRequestEntity): SignUpResult

    suspend fun requestSignOut(): SignOutResult

    suspend fun requestUpdateUserInfo(request: AdditionalInfoEntity): UpdateInfoResult

    suspend fun setUserId(userId: Int)

    suspend fun getUserId(): Int?

    suspend fun setUserEmail(email: String)

    suspend fun getUserEmail(): String?

    suspend fun setUserName(userName: String)

    suspend fun getUserName(): String?
}