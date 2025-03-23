package com.tedmoon99.data.studio.mapper

import com.tedmoon99.data.studio.model.Studio
import com.tedmoon99.domain.studio.model.StudioEntity

object StudioMapper {
    fun toDomain(studio: Studio): StudioEntity {
        return StudioEntity(
            id = studio.id,
            name = studio.name,
            profileImage = studio.profileImage,
            price = studio.price,
            rating = studio.rating,
            imageUrls = studio.imageUrls,
        )
    }

    fun fromDomain(studioEntity: StudioEntity): Studio {
        return Studio(
            id = studioEntity.id,
            name = studioEntity.name,
            profileImage = studioEntity.profileImage,
            price = studioEntity.price,
            rating = studioEntity.rating,
            imageUrls = studioEntity.imageUrls
        )
    }
}