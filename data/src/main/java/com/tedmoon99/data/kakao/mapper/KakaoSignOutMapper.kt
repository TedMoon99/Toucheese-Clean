package com.tedmoon99.data.kakao.mapper

import com.tedmoon99.data.kakao.model.KakaoSignOutResult
import com.tedmoon99.domain.kakao.model.KakaoSignOutResultEntity

object KakaoSignOutMapper {

    fun fromDomain(domain: KakaoSignOutResultEntity): KakaoSignOutResult {
        return KakaoSignOutResult(
            success = domain.success,
            errorMessage = domain.errorMessage
        )
    }

    fun toDomain(data: KakaoSignOutResult): KakaoSignOutResultEntity {
        return KakaoSignOutResultEntity(
            success = data.success,
            errorMessage = data.errorMessage
        )
    }
}