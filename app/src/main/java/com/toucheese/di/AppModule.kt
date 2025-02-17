package com.toucheese.di

import com.tedmoon99.data.datasource.remote.studio.StudioRemoteDataSource
import com.tedmoon99.data.datasource.remote.studio.StudioRemoteDataSourceImpl
import com.tedmoon99.data.datasource.remote.studio.api.StudioService
import com.tedmoon99.data.mapper.studio.StudioMapper
import com.tedmoon99.data.repository.studio.StudioRepositoryImpl
import com.tedmoon99.domain.repository.studio.StudioRepository
import com.tedmoon99.domain.usecase.studio.StudioUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


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

}