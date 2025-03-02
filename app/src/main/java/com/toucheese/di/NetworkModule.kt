package com.toucheese.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tedmoon99.data.BuildConfig.BASE_URL
import com.tedmoon99.data.mapper.paging.SortXAdapter
import com.tedmoon99.data.model.remote.concept.studios.paging.SortX
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

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(SortX::class.java, SortXAdapter())
            .create()
    }

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
                authenticator(tokenAuthenticator)
                connectTimeout(30, TimeUnit.SECONDS) // 서버 연결 대기 시간
                readTimeout(30, TimeUnit.SECONDS) // 서버 응답 대기 시간
                writeTimeout(30, TimeUnit.SECONDS) // 요청 데이터 전송 시간
                retryOnConnectionFailure(true)
                followRedirects(false)// 리다이렉션 방지
                followSslRedirects(false) // SSL 리다이렉션 방지
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
                retryOnConnectionFailure(true)
                followRedirects(false)// 리다이렉션 방지
                followSslRedirects(false) // SSL 리다이렉션 방지
            }.build()
    }

    @BaseClient
    @Provides
    @Singleton
    fun provideBaseRetrofit(
        @BaseClient okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @AuthClient
    @Provides
    @Singleton
    fun provideAuthRetrofit(
        @AuthClient okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseClient
}
