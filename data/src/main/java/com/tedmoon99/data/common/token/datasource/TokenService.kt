package com.tedmoon99.data.common.token.datasource

import com.tedmoon99.data.common.token.model.RefreshTokenDto
import com.tedmoon99.data.common.token.model.RefreshTokenResponse
import com.tedmoon99.data.common.token.model.TokenApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {

    @POST("v1/tokens/reissue")
    suspend fun refreshToken(
        @Body request: RefreshTokenDto
    ): Response<TokenApiResponse<RefreshTokenResponse>>

}