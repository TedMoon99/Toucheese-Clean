package com.toucheese.di

import android.content.Context
import com.tedmoon99.data.datasource.remote.member.api.KakaoService
import com.tedmoon99.data.repository.member.KakaoRepositoryImpl
import com.tedmoon99.domain.repository.member.KakaoRepository
import com.tedmoon99.domain.repository.member.MemberRepository
import com.tedmoon99.domain.repository.member.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SocialModule {

    @Provides
    @Singleton
    fun provideKakaoService(@NetworkModule.AuthClient retrofit: Retrofit): KakaoService = retrofit.create()

    @Provides
    @Singleton
    fun provideKakaoRepository(
        @ApplicationContext context: Context,
        kakaoService: KakaoService,
        tokenRepository: TokenRepository,
        memberRepository: MemberRepository,
    ): KakaoRepository {
        return KakaoRepositoryImpl(context, kakaoService, tokenRepository, memberRepository)
    }
}