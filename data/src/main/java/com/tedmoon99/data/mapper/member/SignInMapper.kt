package com.tedmoon99.data.mapper.member

import com.tedmoon99.data.model.remote.member.sign_in.SignInRequest
import com.tedmoon99.domain.entity.remote.member.SignInRequestEntity

object SignInMapper {

    fun fromDomain(entity: SignInRequestEntity): SignInRequest {
        return SignInRequest(
            email = entity.email,
            password = entity.password,
            deviceId = entity.deviceId,
        )
    }

    fun toDomain(signInRequest: SignInRequest): SignInRequestEntity {
        return SignInRequestEntity(
            email = signInRequest.email,
            password = signInRequest.password,
            deviceId = signInRequest.deviceId
        )
    }
}