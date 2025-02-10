package com.tedmoon99.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tedmoon99.data.datasource.converter.BitmapConverter
import com.tedmoon99.data.datasource.local.dao.TestConceptDao
import com.tedmoon99.data.model.local.TestConcept

@Database(entities = [TestConcept::class], version = 1)
@TypeConverters(BitmapConverter::class)
abstract class TestConceptDatabase: RoomDatabase() {
    abstract fun testConceptDao(): TestConceptDao
}