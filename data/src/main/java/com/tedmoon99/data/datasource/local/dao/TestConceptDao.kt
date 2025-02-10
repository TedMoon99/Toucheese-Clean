package com.tedmoon99.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tedmoon99.data.model.local.TestConcept

@Dao
interface TestConceptDao {

    @Query("SELECT * FROM testconcept")
    suspend fun getAll(): List<TestConcept>

    @Query("SELECT * FROM testconcept WHERE uid IN (:conceptId)")
    suspend fun findById(conceptId: Int): TestConcept

    @Query("SELECT * FROM testconcept WHERE name LIKE :conceptName LIMIT 1")
    suspend fun findByName(conceptName: String): TestConcept

    @Insert
    suspend fun insert(vararg concepts: TestConcept)

    @Delete
    suspend fun delete(vararg concept: TestConcept)
}