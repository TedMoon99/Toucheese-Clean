package com.tedmoon99.data.kakao.datasource

import com.tedmoon99.data.kakao.model.KakaoSignIn
import com.tedmoon99.data.kakao.model.KakaoSignInDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface KakaoService {
    // Kakao SignIn
    @POST("v1/auth/kakao")
    suspend fun requestKakaoSignIn(
        @Body kakaoSignInDto: KakaoSignInDto
    ): Response<KakaoSignIn>
}