package com.tedmoon99.data.datasource.remote.member.api

import com.tedmoon99.data.model.remote.member.sign_in.SignInRequest
import com.tedmoon99.data.model.remote.member.sign_in.SignInResponse
import com.tedmoon99.data.model.remote.response.TokenApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface MemberService {

    @POST("v1/members")
    suspend fun requestSignIn(
        @Body request: SignInRequest
    ): Response<SignInResponse>

    @DELETE("v1/tokens/logout")
    suspend fun requestLogout(
        @Header("Authorization") accessToken: String?,
        @Body deviceId: String?
    ): Response<TokenApiResponse<String>>
}