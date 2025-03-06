package com.tedmoon99.data.mapper.member

import com.tedmoon99.data.model.remote.member.sign_in.KakaoSignOutResult
import com.tedmoon99.domain.entity.remote.member.KakaoSignOutResultEntity

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