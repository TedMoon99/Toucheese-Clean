package com.tedmoon99.data.mapper.member

import com.tedmoon99.data.model.remote.member.sign_up.SignUpDto
import com.tedmoon99.domain.member.model.SignUpRequestEntity

object SignUpMapper {

    fun fromDomain(domain: SignUpRequestEntity): SignUpDto {
        return SignUpDto(
            email = domain.email,
            password =domain.password,
            name = domain.name,
            phone = domain.phone
        )
    }

}