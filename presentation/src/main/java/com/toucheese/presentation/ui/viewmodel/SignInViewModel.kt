package com.toucheese.presentation.ui.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tedmoon99.domain.usecase.member.SignInResult
import com.tedmoon99.domain.usecase.member.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : ViewModel() {
    private val mHandler = Handler(Looper.getMainLooper())

    // 자동 로그인 상태
    private val _autoSignIn = MutableStateFlow(false)
    val autoSignIn: StateFlow<Boolean> = _autoSignIn

    // 자동 로그인 설정
    fun setAuthSignIn(isChecked: Boolean) {
        _autoSignIn.value = isChecked
    }

    // 로그인 요청
    fun requestSignIn(email: String, password: String, callback: (SignInResult) -> Unit) {
        val autoSignInState = autoSignIn.value
        Log.d(TAG, "로그인 요청\n" +
                "email: $email\n" +
                "password: $password\n" +
                "자동 로그인:$autoSignInState"
        )

        viewModelScope.launch {
            val result =  signInUseCase.requestSignIn(email, password)
            Log.d(TAG, "로그인 결과: $result")
            callback(result)
        }
    }

    companion object {
        private const val TAG = "SignInViewModel"
    }
}