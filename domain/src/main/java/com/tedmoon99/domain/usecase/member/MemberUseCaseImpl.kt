package com.tedmoon99.domain.usecase.member

import com.tedmoon99.domain.entity.remote.member.SignInRequestEntity
import com.tedmoon99.domain.intent.member.SignInResult
import com.tedmoon99.domain.intent.member.SignOutResult
import com.tedmoon99.domain.repository.member.MemberRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MemberUseCaseImpl @Inject constructor(
    private val repository: MemberRepository
) : MemberUseCase {
    private val _isSignedOut = MutableStateFlow(true)
    val isSignedOut: StateFlow<Boolean> = _isSignedOut

    // 로그인 요청
    override suspend fun requestSignIn(email: String, password: String): SignInResult {
        // 객체 생성
        val request = SignInRequestEntity(email, password)
        // 로그인 요청
        val result = repository.requestSignIn(request)
        // 로그인 상태 설정
        _isSignedOut.value = false
        // 결과 반환
        return result
    }

    // 로그아웃 요청
    override suspend fun requestSignOut(): SignOutResult {
        val result = repository.requestSignOut()
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

    override fun getSignOutState(): StateFlow<Boolean> {
        return isSignedOut
    }
}