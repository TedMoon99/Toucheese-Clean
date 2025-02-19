package com.toucheese.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SignInViewModel @Inject constructor(

) : ViewModel() {

    // 자동 로그인 상태
    private val _autoSignIn = MutableStateFlow(false)
    val autoSignIn: StateFlow<Boolean> = _autoSignIn

    // 자동 로그인 설정
    fun setAuthSignIn(isChecked: Boolean) {
        _autoSignIn.value = isChecked
    }

    // 로그인 요청
    fun requestSignIn(email: String, password: String) {
        val autoSignInState = autoSignIn.value
        Log.d(TAG, "로그인 요청\n" +
                "email: $email\n" +
                "password: $password\n" +
                "자동 로그인:$autoSignInState"
        )

    }

    companion object {
        private val TAG = "SignInViewModel"
    }
}