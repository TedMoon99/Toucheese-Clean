package com.tedmoon99.data.di.local

import android.content.Context
import androidx.room.Room
import com.tedmoon99.data.datasource.local.dao.TestConceptDao
import com.tedmoon99.data.datasource.local.database.TestConceptDatabase
import com.tedmoon99.data.repository.TestRepositoryImpl
import com.tedmoon99.domain.repository.TestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTestConceptDatabase(@ApplicationContext context: Context): TestConceptDatabase {
        return Room.databaseBuilder(context, TestConceptDatabase::class.java, "test-database").build()
    }

    @Provides
    fun provideTestConceptDao(database: TestConceptDatabase): TestConceptDao {
        return database.testConceptDao()
    }

    @Provides
    fun provideTestRepository(testRepositoryImpl: TestRepositoryImpl): TestRepository{
        return testRepositoryImpl
    }
}