package com.tedmoon99.domain.usecase.member

import com.tedmoon99.domain.entity.remote.member.AdditionalInfoEntity
import com.tedmoon99.domain.entity.remote.member.KakaoSignInResultEntity
import com.tedmoon99.domain.entity.remote.member.SignInRequestEntity
import com.tedmoon99.domain.entity.remote.member.SignUpRequestEntity
import com.tedmoon99.domain.intent.member.SignInResult
import com.tedmoon99.domain.intent.member.SignOutResult
import com.tedmoon99.domain.intent.member.SignUpResult
import com.tedmoon99.domain.intent.member.UpdateInfoResult
import com.tedmoon99.domain.repository.member.KakaoRepository
import com.tedmoon99.domain.repository.member.MemberRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MemberUseCaseImpl @Inject constructor(
    private val memberRepository: MemberRepository,
    private val kakaoRepository: KakaoRepository,
) : MemberUseCase {
    private val _isSignedOut = MutableStateFlow(true)
    val isSignedOut: StateFlow<Boolean> = _isSignedOut

    // 로그인 요청
    override suspend fun requestSignIn(email: String, password: String): SignInResult {
        // 객체 생성
        val request = SignInRequestEntity(email, password)
        // 로그인 요청
        val result = memberRepository.requestSignIn(request)
        // 로그인 상태 설정
        _isSignedOut.value = false
        // 결과 반환
        return result
    }

    override suspend fun requestKakaoSignIn(): KakaoSignInResultEntity {
        return kakaoRepository.requestKakaoSignIn()
    }

    // 로그아웃 요청
    override suspend fun requestSignOut(): SignOutResult {
        // 카카오톡으로 로그인 한 경우
        val isKakaoSignIn = kakaoRepository.isKakaoSignIn()

        if (isKakaoSignIn) kakaoRepository.requestKakaoSignOut()

        val result = memberRepository.requestSignOut()

        when (result){
            is SignOutResult.Success -> {
                _isSignedOut.value = true
            }
            is SignOutResult.Failure -> {
                _isSignedOut.value = false
            }
        }
        return result
    }

    override suspend fun requestUpdateUserInfo(name: String, phone: String): UpdateInfoResult {
        val requestDto = AdditionalInfoEntity(name, phone)
        return memberRepository.requestUpdateUserInfo(requestDto)
    }

    override suspend fun requestSignUp(
        email: String,
        password: String,
        name: String,
        phone: String
    ): SignUpResult {
        val request = SignUpRequestEntity(email, password, name, phone)
        return memberRepository.requestSignUp(request)
    }

    override fun getSignOutState(): StateFlow<Boolean> {
        return isSignedOut
    }
}