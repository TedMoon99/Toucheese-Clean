package com.tedmoon99.data.mapper.studio

import com.tedmoon99.data.model.remote.concept.studios.content.Studio
import com.tedmoon99.domain.entity.remote.concept.studios.StudioEntity

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