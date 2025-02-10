package com.tedmoon99.data.mapper

import com.tedmoon99.data.model.local.TestConcept
import com.tedmoon99.domain.entity.local.TestConceptEntity

object TestConceptMapper {
    // 단일 변환
    fun toDomain(testConcept: TestConcept): TestConceptEntity {
        return TestConceptEntity(
            uid = testConcept.uid,
            name = testConcept.name,
            image = testConcept.imageData
        )
    }

    // 단일 변환
    fun fromDomain(entity: TestConceptEntity): TestConcept {
        return TestConcept(
            uid = entity.uid,
            name = entity.name,
            imageData = entity.image
        )
    }
}