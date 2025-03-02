package com.toucheese.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tedmoon99.data.datasource.remote.member.api.MemberService
import com.tedmoon99.data.repository.member.MemberRepositoryImpl
import com.tedmoon99.domain.repository.member.MemberRepository
import com.tedmoon99.domain.repository.member.TokenRepository
import com.tedmoon99.domain.usecase.member.MemberUseCase
import com.tedmoon99.domain.usecase.member.MemberUseCaseImpl
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
        memberService: MemberService,
        dataStore: DataStore<Preferences>,
    ): MemberRepository {
        return MemberRepositoryImpl(tokenRepository, memberService, dataStore)
    }

    @Provides
    @Singleton
    fun provideMemberService(
        @NetworkModule.AuthClient retrofit: Retrofit
    ): MemberService = retrofit.create()

    @Provides
    @Singleton
    fun provideMemberUseCase(memberRepository: MemberRepository): MemberUseCase {
        return MemberUseCaseImpl(memberRepository)
    }
}