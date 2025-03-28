package com.tedmoon99.data.member.mapper

import com.tedmoon99.data.member.model.AdditionalInfoDto
import com.tedmoon99.domain.member.model.AdditionalInfoEntity

object AdditionalInfoMapper {

    fun fromDomain(domain: AdditionalInfoEntity): AdditionalInfoDto {
        return AdditionalInfoDto(domain.name, domain.phone)
    }

    fun toDomain(data: AdditionalInfoDto): AdditionalInfoEntity {
        return AdditionalInfoEntity(data.name, data.phone)
    }
}