package com.toucheese.di

import com.tedmoon99.data.studio.datasource.StudioRemoteDataSource
import com.tedmoon99.data.studio.datasource.StudioRemoteDataSourceImpl
import com.tedmoon99.data.studio.datasource.StudioService
import com.tedmoon99.data.studio.mapper.StudioMapper
import com.tedmoon99.data.studio.repository.StudioRepositoryImpl
import com.tedmoon99.domain.studio.repository.StudioRepository
import com.tedmoon99.domain.studio.usecase.StudioUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object StudioModule {


    @Provides
    @Singleton
    fun provideStudioRemoteDataSource(api: StudioService): StudioRemoteDataSource {
        return StudioRemoteDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideStudioRepository(remoteDataSource: StudioRemoteDataSource): StudioRepository {
        return StudioRepositoryImpl(remoteDataSource, StudioMapper)
    }

    @Provides
    @Singleton
    fun provideStudioUseCase(repository: StudioRepository): StudioUseCase {
        return StudioUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideStudioService(
        @NetworkModule.BaseClient retrofit: Retrofit
    ): StudioService = retrofit.create()

}