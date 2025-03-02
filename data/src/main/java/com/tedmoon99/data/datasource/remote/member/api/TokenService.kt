package com.tedmoon99.data.datasource.remote.member.api

import com.tedmoon99.data.model.remote.member.token.RefreshTokenRequest
import com.tedmoon99.data.model.remote.member.token.RefreshTokenResponse
import com.tedmoon99.data.model.remote.response.TokenApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {

    @POST("v1/tokens/reissue")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): Response<TokenApiResponse<RefreshTokenResponse>>

}