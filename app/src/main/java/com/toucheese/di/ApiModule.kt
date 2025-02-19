package com.toucheese.di

import com.tedmoon99.data.datasource.remote.studio.api.StudioService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideStudioService(retrofit: Retrofit): StudioService {
        return retrofit.create(StudioService::class.java)
    }
}