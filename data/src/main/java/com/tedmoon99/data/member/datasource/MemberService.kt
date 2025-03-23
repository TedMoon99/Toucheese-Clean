package com.tedmoon99.data.member.datasource

import com.tedmoon99.data.common.token.model.TokenApiResponse
import com.tedmoon99.data.member.model.AdditionalInfoDto
import com.tedmoon99.data.member.model.SignInDto
import com.tedmoon99.data.member.model.SignInResponse
import com.tedmoon99.data.member.model.SignUpDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT

interface MemberService {

    @POST("v1/members")
    suspend fun requestSignIn(
        @Body request: SignInDto
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

    @POST("v1/members/signup")
    suspend fun requestSignUp(
        @Body signUpDto: SignUpDto
    ): Response<Unit>

}