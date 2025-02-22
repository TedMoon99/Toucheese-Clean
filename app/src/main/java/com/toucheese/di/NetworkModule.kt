package com.toucheese.di

import com.tedmoon99.data.BuildConfig.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @BaseClient
    @Provides
    @Singleton
    fun provideBaseClient(
        tokenInterceptor: Interceptor,
        tokenAuthenticator: Authenticator
    ): OkHttpClient {
        return OkHttpClient
            .Builder().run {
                addInterceptor(tokenInterceptor)
                connectTimeout(30, TimeUnit.SECONDS) // 서버 연결 대기 시간
                readTimeout(30, TimeUnit.SECONDS) // 서버 응답 대기 시간
                writeTimeout(30, TimeUnit.SECONDS) // 요청 데이터 전송 시간
                authenticator(tokenAuthenticator)
            }.build()
    }

    @AuthClient
    @Provides
    @Singleton
    fun provideAuthClient(
        tokenInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient
            .Builder().run {
                addInterceptor(tokenInterceptor)
                connectTimeout(30, TimeUnit.SECONDS) // 서버 연결 대기 시간
                readTimeout(30, TimeUnit.SECONDS) // 서버 응답 대기 시간
                writeTimeout(30, TimeUnit.SECONDS) // 요청 데이터 전송 시간
            }.build()
    }

    @BaseClient
    @Provides
    @Singleton
    fun provideBaseRetrofit(@BaseClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @AuthClient
    @Provides
    @Singleton
    fun provideAuthRetrofit(@AuthClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseClient
}
