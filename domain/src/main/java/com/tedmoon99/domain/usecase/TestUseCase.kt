package com.tedmoon99.domain.usecase

import com.tedmoon99.domain.entity.local.TestConceptEntity
import com.tedmoon99.domain.repository.TestRepository

class TestUseCase(private val testRepository: TestRepository) {

    // 모든 컨셉 조회
    suspend fun getAllConcept(): List<TestConceptEntity> {
        return testRepository.getAll()
    }
}