package com.tedmoon99.domain.repository.member

import com.tedmoon99.domain.entity.remote.member.KakaoSignInResultEntity
import com.tedmoon99.domain.entity.remote.member.KakaoSignOutResultEntity

interface KakaoRepository {

    fun isKakaoSignIn(): Boolean

    suspend fun requestKakaoSignIn(): KakaoSignInResultEntity

    suspend fun requestKakaoSignOut(): KakaoSignOutResultEntity
}