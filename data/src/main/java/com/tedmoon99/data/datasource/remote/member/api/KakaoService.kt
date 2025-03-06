package com.tedmoon99.data.datasource.remote.member.api

import com.tedmoon99.data.model.remote.member.sign_in.KakaoSignIn
import com.tedmoon99.data.model.remote.member.sign_in.KakaoSignInDto
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