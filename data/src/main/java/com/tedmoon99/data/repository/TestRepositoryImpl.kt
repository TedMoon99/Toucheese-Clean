package com.tedmoon99.data.repository

import com.tedmoon99.data.datasource.local.dao.TestConceptDao
import com.tedmoon99.data.mapper.TestConceptMapper
import com.tedmoon99.domain.entity.local.TestConceptEntity
import com.tedmoon99.domain.repository.TestRepository
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val testConceptDao: TestConceptDao,
    private val testConceptMapper: TestConceptMapper,
): TestRepository {

    override suspend fun getAll(): List<TestConceptEntity> = testConceptDao.getAll().map(testConceptMapper::toDomain)

    override suspend fun findById(conceptId: Int): TestConceptEntity {
        return testConceptMapper.toDomain(testConceptDao.findById(conceptId))
    }

    override suspend fun findByName(conceptName: String): TestConceptEntity {
        return testConceptMapper.toDomain(testConceptDao.findByName(conceptName))
    }

    override suspend fun insert(vararg testConceptEntity: TestConceptEntity) {
        testConceptDao.insert(*testConceptEntity.map(testConceptMapper::fromDomain).toTypedArray())
    }

    override suspend fun delete(vararg testConceptEntity: TestConceptEntity) {
        testConceptDao.delete(*testConceptEntity.map(testConceptMapper::fromDomain).toTypedArray())
    }

}