package com.tedmoon99.data.mapper.member

import com.tedmoon99.data.model.remote.member.sign_up.AdditionalInfoDto
import com.tedmoon99.domain.member.model.AdditionalInfoEntity

object AdditionalInfoMapper {

    fun fromDomain(domain: AdditionalInfoEntity): AdditionalInfoDto {
        return AdditionalInfoDto(domain.name, domain.phone)
    }

    fun toDomain(data: AdditionalInfoDto): AdditionalInfoEntity {
        return AdditionalInfoEntity(data.name, data.phone)
    }
}