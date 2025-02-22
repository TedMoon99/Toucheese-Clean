package com.toucheese.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.tedmoon99.data.repository.member.token.TokenAuthenticator
import com.tedmoon99.data.repository.member.token.TokenInterceptor
import com.tedmoon99.data.repository.member.token.TokenRepositoryImpl
import com.tedmoon99.data.repository.member.token.TokenService
import com.tedmoon99.domain.repository.member.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TokenModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideTokenRepository(
        dataStore: DataStore<Preferences>
    ): TokenRepository {
        return TokenRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideTokenService(
        @NetworkModule.AuthClient retrofit: Retrofit
    ): TokenService = retrofit.create()

    @Provides
    @Singleton
    fun provideTokenInterceptor(
        tokenRepository: TokenRepository
    ): Interceptor {
        return TokenInterceptor(tokenRepository)
    }

    @Provides
    @Singleton
    fun provideTokenAuthenticator(
        @ApplicationContext context: Context,
        tokenService: TokenService,
        tokenRepository: TokenRepository,
    ): Authenticator {
        return TokenAuthenticator(context, tokenService, tokenRepository)
    }
}