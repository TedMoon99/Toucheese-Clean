package com.toucheese.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tedmoon99.data.repository.member.sign_in.MemberRepositoryImpl
import com.tedmoon99.data.repository.member.sign_in.SignInService
import com.tedmoon99.domain.repository.member.MemberRepository
import com.tedmoon99.domain.repository.member.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MemberModule {

    @Provides
    @Singleton
    fun provideMemberRepository(
        tokenRepository: TokenRepository,
        signInService: SignInService,
        dataStore: DataStore<Preferences>,
    ): MemberRepository {
        return MemberRepositoryImpl(tokenRepository, signInService, dataStore)
    }

    @Provides
    @Singleton
    fun provideSignInService(
        @NetworkModule.AuthClient retrofit: Retrofit
    ): SignInService = retrofit.create()
}