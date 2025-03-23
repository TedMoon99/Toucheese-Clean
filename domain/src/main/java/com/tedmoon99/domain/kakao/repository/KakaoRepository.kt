package com.tedmoon99.domain.kakao.repository

import com.tedmoon99.domain.kakao.model.KakaoSignInResultEntity
import com.tedmoon99.domain.kakao.model.KakaoSignOutResultEntity

interface KakaoRepository {

    fun isKakaoSignIn(): Boolean

    suspend fun requestKakaoSignIn(): KakaoSignInResultEntity

    suspend fun requestKakaoSignOut(): KakaoSignOutResultEntity
}