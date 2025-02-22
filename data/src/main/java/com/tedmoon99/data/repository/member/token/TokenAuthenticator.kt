package com.tedmoon99.data.repository.member.token

import android.content.Context
import android.content.Intent
import android.util.Log
import com.tedmoon99.data.model.remote.member.token.RefreshTokenRequest
import com.tedmoon99.domain.repository.member.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val context: Context,
    private val tokenService: TokenService,
    private val tokenRepository: TokenRepository
) : Authenticator {

    // 401 UnAuthorization 발생 시 자동 호출
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            // 토큰 재발급 요청
            requestRefreshToken()
            // 새로운 액세스 토큰 조회
            val newToken = tokenRepository.getAccessToken()

            return@runBlocking if (!newToken.isNullOrEmpty()) {
                Log.d(TAG, "토큰 재발급 완료: $newToken")
                // 새로운 토큰으로 요청 재전송
                val newRequest = response.request.newBuilder()
                    .addHeader("Authorization", "Bearer $newToken")
                    .build()
                newRequest
            } else {
                Log.e(TAG, "토큰 재발급 실패")
                // 기존 토큰 삭제
                tokenRepository.deleteTokens()
                // 로그인 화면으로 이동
                triggerLogout()
                null
            }
        }
    }

    private suspend fun requestRefreshToken() {
        try {
            // Refresh 토큰 조회
            val oldToken = tokenRepository.getRefreshToken()
            // 새로운 재발급 토큰 요청
            val refreshTokenRequest = RefreshTokenRequest(oldToken, null)
            val response = tokenService.refreshToken(refreshTokenRequest)

            // Access 토큰 & Refresh 토큰 저장
            val header = response.headers()["Authorization"]

            if (header != null) {
                // 받은 Access 토큰 저장
                val token = header.removePrefix("Bearer ")
                tokenRepository.setAccessToken(token)

                // Refresh 토큰 저장
                val body = response.body()
                if (body != null) {
                    val newToken = body.refreshToken
                    // Refresh 토큰 저장
                    tokenRepository.setRefreshToken(newToken)
                }
            }
        } catch (error: Exception) {
            Log.e(TAG, "Refresh 토큰 조회 오류: ${error.message}")
        }
    }

    private fun triggerLogout() {
        val intent = Intent(INTENT_LOGOUT)
        context.sendBroadcast(intent)
    }

    companion object {
        private const val INTENT_LOGOUT = "LOGOUT"
        private const val TAG = "TokenAuthenticator"
    }

}