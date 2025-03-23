package com.tedmoon99.data.kakao.repository

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.tedmoon99.data.kakao.datasource.KakaoService
import com.tedmoon99.data.kakao.mapper.KakaoSignInMapper
import com.tedmoon99.data.kakao.mapper.KakaoSignOutMapper
import com.tedmoon99.data.kakao.model.KakaoSignInDto
import com.tedmoon99.data.kakao.model.KakaoSignInResult
import com.tedmoon99.data.kakao.model.KakaoSignOutResult
import com.tedmoon99.domain.kakao.model.KakaoSignInResultEntity
import com.tedmoon99.domain.kakao.model.KakaoSignOutResultEntity
import com.tedmoon99.domain.kakao.repository.KakaoRepository
import com.tedmoon99.domain.member.repository.MemberRepository
import com.tedmoon99.domain.member.repository.TokenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class KakaoRepositoryImpl @Inject constructor(
    private val context: Context,
    private val kakaoService: KakaoService,
    private val tokenRepository: TokenRepository,
    private val memberRepository: MemberRepository,
) : KakaoRepository {

    private val _isKakaoSignIn = MutableStateFlow(false)
    private val isKakaoSignIn: StateFlow<Boolean> = _isKakaoSignIn

    // 카카오 로그인 여부 반환
    override fun isKakaoSignIn(): Boolean {
        return isKakaoSignIn.value
    }

    // 카카오 로그인 요청
    override suspend fun requestKakaoSignIn(): KakaoSignInResultEntity {
        return try {
            val token = suspendCoroutine { continuation ->
                // 카카오 플랫폼 서버에 카카오 로그인 요청
                UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                    if (error != null) {
                        continuation.resumeWithException(error)
                    } else if (token != null) {
                        continuation.resume(token)
                    }
                }
            }

            // 로그인 성공 후 서버 요청
            val kakaoSignInResult = sendSignInRequest(token)

            KakaoSignInMapper.toDomain(kakaoSignInResult)
        } catch (e: Throwable) {
            Log.e(TAG, "카카오 로그인 과정 중 오류 발생", e)
            KakaoSignInResultEntity(success = false)
        }
    }

    override suspend fun requestKakaoSignOut(): KakaoSignOutResultEntity {
        return try {
            val result = suspendCoroutine { continuation ->
                // 카카오 플랫폼 서버에 카카오 로그아웃 요청
                UserApiClient.instance.logout { error: Throwable? ->
                    if (error != null) {
                        Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                        continuation.resumeWithException(error)
                    } else {
                        Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                        continuation.resume(true)
                    }
                }
            }
            // 카카오 로그인 상태 업데이트
            _isKakaoSignIn.value = result
            Log.i(TAG, "카카오 로그아웃 상태: ${result}")

            val signOutResult = KakaoSignOutResult(success = result)

            KakaoSignOutMapper.toDomain(signOutResult)
        } catch (e: Throwable) {
            Log.e(TAG, "카카오 로그아웃 과정 중 오류 발생", e)
            val signOutResult = KakaoSignOutResult(success = false, errorMessage = "${e.message}")
            KakaoSignOutMapper.toDomain(signOutResult)
        }
    }

    // 서버에 카카오 로그인 정보를 전달
    // 사용자 정보 저장
    private suspend fun sendSignInRequest(token: OAuthToken): KakaoSignInResult {
        val kakaoAccessToken = token.accessToken
        val kakaoIdToken = token.idToken

        val kakaoSignInDto = KakaoSignInDto(
            idToken = kakaoIdToken ?: "",
            accessToken = kakaoAccessToken,
            platform = "KAKAO",
            deviceId = null // 초기값 X -> null
        )

        val response = kakaoService.requestKakaoSignIn(kakaoSignInDto)

        return if (response.isSuccessful && response.code() == 200) {

            val accessToken = response.headers()["Authorization"]?.removePrefix("Bearer ") ?: ""

            response.body()?.let {
                // 유저 정보 저장
                memberRepository.run {
                    setUserId(it.memberId)
                    setUserName(it.nickname)
                    setUserEmail(it.email)
                }
                // 토큰 정보 저장
                tokenRepository.run {
                    setAccessToken(accessToken)
                    setRefreshToken(it.refreshToken)
                    setDeviceId(it.deviceId)
                }
                // 카카오 로그인 상태 업데이트
                _isKakaoSignIn.value = true
                Log.i(TAG, "카카오 로그인 상태: ${true}")

                KakaoSignInResult(success = true, isFirstLogin = it.isFirstLogin)
            } ?: KakaoSignInResult(success = false)
        } else {
            Log.e(TAG, "카카오 로그인 오류 코드: ${response.code()}")
            KakaoSignInResult(success = false)
        }
    }

    companion object {
        private const val TAG = "KakaoRepositoryImpl"
    }
}