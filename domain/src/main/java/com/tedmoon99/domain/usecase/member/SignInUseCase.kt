package com.tedmoon99.domain.usecase.member

import com.tedmoon99.domain.entity.auth.sign_in.SignInRequestEntity
import com.tedmoon99.domain.repository.member.MemberRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: MemberRepository
) {

    // 로그인 요청
    suspend fun requestSignIn(email: String, password: String): SignInResult {
        // 객체 생성
        val request = SignInRequestEntity(email, password)
        // 로그인 요청
        val result = repository.requestSignIn(request)
        // 결과 반환
        return result
    }
}