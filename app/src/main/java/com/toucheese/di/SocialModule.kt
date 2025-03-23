package com.toucheese.di

import android.content.Context
import com.tedmoon99.data.kakao.datasource.KakaoService
import com.tedmoon99.data.kakao.repository.KakaoRepositoryImpl
import com.tedmoon99.domain.kakao.repository.KakaoRepository
import com.tedmoon99.domain.member.repository.MemberRepository
import com.tedmoon99.domain.member.repository.TokenRepository
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