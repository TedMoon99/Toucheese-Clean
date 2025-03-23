package com.toucheese.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tedmoon99.data.member.datasource.MemberService
import com.tedmoon99.data.member.repository.MemberRepositoryImpl
import com.tedmoon99.domain.kakao.repository.KakaoRepository
import com.tedmoon99.domain.member.repository.MemberRepository
import com.tedmoon99.domain.member.repository.TokenRepository
import com.tedmoon99.domain.member.usecase.MemberUseCase
import com.tedmoon99.domain.member.usecase.MemberUseCaseImpl
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
    fun provideMemberUseCase(
        memberRepository: MemberRepository,
        kakaoRepository: KakaoRepository,
    ): MemberUseCase {
        return MemberUseCaseImpl(memberRepository, kakaoRepository)
    }
}