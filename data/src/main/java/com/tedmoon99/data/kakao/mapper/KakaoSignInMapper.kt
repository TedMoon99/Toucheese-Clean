package com.tedmoon99.data.kakao.mapper

import com.tedmoon99.data.kakao.model.KakaoSignInResult
import com.tedmoon99.domain.kakao.model.KakaoSignInResultEntity

object KakaoSignInMapper {

    fun fromDomain(entity: KakaoSignInResultEntity): KakaoSignInResult {
        return KakaoSignInResult(
            success = entity.success,
            isFirstLogin = entity.isFirstLogin
        )
    }

    fun toDomain(result: KakaoSignInResult): KakaoSignInResultEntity {
        return KakaoSignInResultEntity(
            success = result.success,
            isFirstLogin = result.isFirstLogin
        )
    }
}