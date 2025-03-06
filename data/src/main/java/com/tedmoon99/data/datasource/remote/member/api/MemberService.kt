package com.tedmoon99.data.datasource.remote.member.api

import com.tedmoon99.data.model.remote.member.sign_in.SignInRequest
import com.tedmoon99.data.model.remote.member.sign_in.SignInResponse
import com.tedmoon99.data.model.remote.member.sign_up.AdditionalInfoDto
import com.tedmoon99.data.model.remote.response.TokenApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface MemberService {

    @POST("v1/members")
    suspend fun requestSignIn(
        @Body request: SignInRequest
    ): Response<SignInResponse>

    @DELETE("v1/tokens/logout")
    suspend fun requestLogout(
        @Body deviceId: String?
    ): Response<TokenApiResponse<String>>

    // 첫 로그인 회원 정보 변경
    @PUT("v1/members")
    suspend fun updateUserInfo(
        @Body additionalInfo: AdditionalInfoDto
    ): Response<Unit>
}