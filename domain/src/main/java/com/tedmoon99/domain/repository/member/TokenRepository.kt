package com.tedmoon99.domain.repository.member

interface TokenRepository {

    suspend fun setAccessToken(accessToken: String)

    suspend fun setRefreshToken(refreshToken: String)

    suspend fun setDeviceId(deviceId: String)

    suspend fun getAccessToken(): String?

    suspend fun getRefreshToken(): String?

    suspend fun getDeviceId(): String?

    suspend fun deleteTokens()

}