package com.tedmoon99.data.datasource.remote.member

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.tedmoon99.data.datasource.remote.member.api.TokenService
import com.tedmoon99.data.model.remote.member.token.RefreshTokenRequest
import com.tedmoon99.domain.repository.member.TokenRepository
import com.tedmoon99.domain.usecase.member.MemberUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenService: TokenService,
    private val tokenRepository: TokenRepository,
    private val memberUseCase: MemberUseCase
) : Authenticator {

    // 중복 실행 방지
    private var mutex = Mutex()
    // 401 UnAuthorization 발생 시 자동 호출
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 3) {
            Log.e(TAG, "토큰 재발행 실패 3회 이상 -> 강제 로그아웃")
            forceSignOut()
            return null
        }

        if (mutex.isLocked) return null

        val currentTime = System.currentTimeMillis()
        if ((currentTime - lastRefreshTime) < MIN_REFRESH_INTERVAL) return null

        response.request.header("Authorization") ?: return null

        return runBlocking(Dispatchers.IO) {
            mutex.withLock {
                try {
                    val newToken = requestRefreshToken() ?: ""
                    Log.d(TAG, "새로운 Access 토큰 : ${newToken}")

                    response.request.newBuilder()
                        .removeHeader("Authorization")
                        .addHeader("Authorization", "Bearer $newToken")
                        .build()
                } catch (error: HttpException) {
                    Log.e(TAG, "토큰 재발급 실패01 : ${error.message}")
                    forceSignOut()
                    null
                }
            }
        }
    }

    private suspend fun requestRefreshToken(): String? {
        return try {
            val oldToken = tokenRepository.getRefreshToken() ?: ""
            val deviceId = tokenRepository.getDeviceId() ?: ""
            val refreshTokenRequest = RefreshTokenRequest(refreshToken = oldToken, deviceId = deviceId)
            Log.i(TAG, "$refreshTokenRequest")
            val response = runBlocking { tokenService.refreshToken(refreshTokenRequest) }
            Log.i(TAG, "Refresh 토큰 조회 상태 코드 : ${response.code()}")
            val responseBody = response.body()

            // 요청 실패
            if (!response.isSuccessful) {
                Log.e(TAG, "토큰 재발행 실패02 : ${response.errorBody()?.string()}")
                return null
            }

            responseBody?.error?.let { error ->
                Log.e(TAG, "토큰 갱신 실패: ${error.code}-${error.message}")
                handleRefreshTokenError(error.code)
                return null
            }

            val newToken = response.headers()["Authorization"]?.removePrefix("Bearer ")
            // Access 토큰, Refresh 토큰, DeviceId 저장
            newToken?.let {
                tokenRepository.setAccessToken(it)
                responseBody?.data?.refreshToken?.let { newRefreshToken ->
                    tokenRepository.setRefreshToken(newRefreshToken)
                }
                responseBody?.data?.deviceId?.let { newDeviceId ->
                    tokenRepository.setDeviceId(newDeviceId)
                }
            }
            newToken
        } catch (error: Exception) {
            Log.e(TAG, "토큰 재발급 중 오류 발생: ${error.message}")
            null
        }
    }

    private fun handleRefreshTokenError(errorCode: Int){
        when (errorCode) {
            4001 -> {
                // Refresh 토큰이 만료된 경우
                Log.e(TAG, "Refresh 토큰이 만료되었습니다")
                forceSignOut()
            }

            4002 -> {
                // Refresh 토큰이 유효하지 않은 경우
                Log.e(TAG, "Refresh 토큰이 유효하지 않습니다")
            }

            4003 -> {
                // Refresh 토큰-DeviceId 의 값의 쌍이 서버에 저장된 값과 다른 경우
                Log.e(TAG, "Refresh 토큰-DeviceId가 서버와 다릅니다")
            }

            4020 -> {
                // Refresh 토큰이 잘못된 형식인 경우
                Log.e(TAG, "Refresh 토큰이 잘못된 형식입니다")
            }

            else -> {
                Log.e(TAG, "예상치 못한 오류 발생 -> 로그아웃")
                forceSignOut()
            }
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 0
        var currentResponse: Response? = response
        while (currentResponse != null) {
            count++
            currentResponse = currentResponse.priorResponse
        }
        return count
    }

    private fun forceSignOut() {
        runBlocking(Dispatchers.IO) {
            memberUseCase.requestSignOut()
        }
    }

    companion object {
        private const val TAG = "TokenAuthenticator"
        private const val MIN_REFRESH_INTERVAL = 5000L // 5초 제한
        private var lastRefreshTime = 0L
    }
}