package com.tedmoon99.data.member.mapper

import com.tedmoon99.data.member.model.SignInDto
import com.tedmoon99.domain.member.model.SignInRequestEntity

object SignInMapper {

    fun fromDomain(entity: SignInRequestEntity): SignInDto {
        return SignInDto(
            email = entity.email,
            password = entity.password,
            deviceId = entity.deviceId,
        )
    }

    fun toDomain(signInRequest: SignInDto): SignInRequestEntity {
        return SignInRequestEntity(
            email = signInRequest.email,
            password = signInRequest.password,
            deviceId = signInRequest.deviceId
        )
    }
}