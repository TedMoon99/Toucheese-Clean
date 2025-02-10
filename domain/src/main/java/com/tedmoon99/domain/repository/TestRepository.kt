package com.tedmoon99.domain.repository

import com.tedmoon99.domain.entity.local.TestConceptEntity

interface TestRepository {

    suspend fun getAll(): List<TestConceptEntity>

    suspend fun findById(conceptId: Int): TestConceptEntity

    suspend fun findByName(conceptName: String): TestConceptEntity

    suspend fun insert(vararg testConceptEntity: TestConceptEntity)

    suspend fun delete(vararg testConceptEntity: TestConceptEntity)

}