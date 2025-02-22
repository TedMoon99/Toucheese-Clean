package com.tedmoon99.data.repository.member.token

import com.tedmoon99.data.model.remote.member.token.RefreshTokenRequest
import com.tedmoon99.data.model.remote.member.token.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {

    @POST("v1/tokens/reissue")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): Response<RefreshTokenResponse>

}