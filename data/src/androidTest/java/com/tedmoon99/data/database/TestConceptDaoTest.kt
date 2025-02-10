package com.tedmoon99.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tedmoon99.data.datasource.local.dao.TestConceptDao
import com.tedmoon99.data.datasource.local.database.TestConceptDatabase
import com.tedmoon99.data.model.local.TestConcept
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking

import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class TestConceptDaoTest {

    private lateinit var database: TestConceptDatabase
    private lateinit var testConceptDao: TestConceptDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room
            .inMemoryDatabaseBuilder(context, TestConceptDatabase::class.java)
            .allowMainThreadQueries() // 테스트용으로 Main Thread에서 실행을 허용
            .build()

        testConceptDao = database.testConceptDao()
    }

    @Test
    fun insert_0() = runBlocking {
        // Given
        val testConcept1 = TestConcept(uid = 1, name = "Concept1", null)
        val testConcept2 = TestConcept(uid = 2, name = "Concept2", null)

        // When
        testConceptDao.insert(testConcept1, testConcept2)

        // Then
        val allConcepts = testConceptDao.getAll()
        assertEquals(2 ,allConcepts.size)
    }

    @Test
    fun insert_X() = runBlocking {
        // Given
        val testConcept1 = TestConcept(uid = 1, name = "Concept1", null)
        val testConcept2 = TestConcept(uid = 2, name = "Concept2", null)

        // When
        testConceptDao.insert(testConcept1, testConcept2)

        // Then
        val allConcepts = testConceptDao.getAll()
        assertNotEquals(3 ,allConcepts.size)
    }

    @Test
    fun getAll_O() = runBlocking {
        // Given
        val expectedConcepts = listOf(
            TestConcept(uid = 1, name = "Concept1", null),
            TestConcept(uid = 2, name = "Concept2", null),
        )

        val insertConcepts = arrayOf(
            TestConcept(1, "Concept1", null),
            TestConcept(2, "Concept2", null),
        )
        // When
        testConceptDao.insert(*insertConcepts)
        val resultConcepts = testConceptDao.getAll()
        // Then
        assertEquals(expectedConcepts, resultConcepts)
    }

    @Test
    fun getAll_X() = runBlocking {
        // Given
        val expectedConcepts = listOf(
            TestConcept(uid = 1, name = "Concept1", null),
            TestConcept(uid = 3, name = "Concept3", null),
        )

        val insertConcepts = arrayOf(
            TestConcept(1, "Concept1", null),
            TestConcept(2, "Concept2", null),
        )
        // When
        testConceptDao.insert(*insertConcepts)
        val resultConcepts = testConceptDao.getAll()
        // Then
        assertNotEquals(expectedConcepts, resultConcepts)
    }

    @Test
    fun delete_0() = runBlocking {
        // Given
        val testConcept1 = TestConcept(uid = 1, name = "Concept1", null)
        val testConcept2 = TestConcept(uid = 2, name = "Concept2", null)
        val insertConcepts = arrayOf(testConcept1, testConcept2)

        testConceptDao.insert(*insertConcepts)

        // When
        testConceptDao.delete(testConcept2)

        // Then
        val currentDB = testConceptDao.getAll()
        assertEquals(1, currentDB.size)
        assertEquals(testConcept1, currentDB[0])
    }

    @Test
    fun delete_X() = runBlocking {
        // Given
        val testConcept1 = TestConcept(uid = 1, name = "Concept1", null)
        val testConcept2 = TestConcept(uid = 2, name = "Concept2", null)
        val insertConcepts = arrayOf(testConcept1, testConcept2)

        testConceptDao.insert(*insertConcepts)

        // When
        testConceptDao.delete(testConcept2)

        // Then
        val currentDB = testConceptDao.getAll()
        assertNotEquals(2, currentDB.size)
        assertNotEquals(testConcept2, currentDB[0])
    }

    @Test
    fun findById_O() = runBlocking {
        // Given
        val testConcept = TestConcept(uid = 1, name = "생동감 있는 느낌", null)
        val insertConcepts = arrayOf(testConcept)

        testConceptDao.insert(*insertConcepts)

        // When
        val findConcept = testConceptDao.findById(1)

        // Then
        assertEquals("생동감 있는 느낌", findConcept.name)
    }

    @Test
    fun findById_X() = runBlocking {
        // Given
        val testConcept = TestConcept(uid = 1, name = "생동감 있는 느낌", null)
        val insertConcepts = arrayOf(testConcept)

        testConceptDao.insert(*insertConcepts)

        // When
        val findConcept = testConceptDao.findById(1)

        // Then
        assertNotEquals("블루/블랙 느낌", findConcept.name)
    }

    @Test
    fun findByName_0() = runBlocking {
        // Given
        val testConcept = TestConcept(uid = 1, name = "생동감 있는 느낌", null)
        val insertConcepts = arrayOf(testConcept)

        testConceptDao.insert(*insertConcepts)

        // When
        val findConcept = testConceptDao.findByName("생동감 있는 느낌")

        // Then
        assertEquals(1, findConcept.uid)
    }

    @Test
    fun findByName_X() = runBlocking {
        // Given
        val testConcept = TestConcept(uid = 1, name = "생동감 있는 느낌", null)
        val insertConcepts = arrayOf(testConcept)

        testConceptDao.insert(*insertConcepts)

        // When
        val findConcept = testConceptDao.findByName("생동감 있는 느낌")

        // Then
        assertNotEquals(2, findConcept.uid)
    }

    @After
    fun tearDown() {
        if (::database.isInitialized) {
            database.close()
        } else {
            println("Database is not initialized")
        }
    }
}