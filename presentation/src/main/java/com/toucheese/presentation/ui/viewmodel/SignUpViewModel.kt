package com.toucheese.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tedmoon99.domain.intent.member.UpdateInfoResult
import com.tedmoon99.domain.usecase.member.MemberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val memberUseCase: MemberUseCase,
) : ViewModel() {

    // 이름
    private val _nameState = MutableStateFlow("")
    val nameState: StateFlow<String> = _nameState

    // 연락처
    private val _contactState = MutableStateFlow("")
    val contactState: StateFlow<String> = _contactState

    // 이름 유효성
    private val _isValidateName = MutableStateFlow(false)
    val isValidateName: StateFlow<Boolean> = _isValidateName

    // 연락처 유효성
    private val _isValidateContact = MutableStateFlow(false)
    val isValidateContact: StateFlow<Boolean> = _isValidateContact

    init {
        // 이름 유효성 검사
        viewModelScope.launch {
            _nameState
                .debounce(300)
                .collect { name ->
                    isValidateName()
                }
        }
        // 연락처 유효성 검사
        viewModelScope.launch {
            _contactState
                .debounce(300)
                .collect { name ->
                    isValidateContact()
                }
        }
    }

    // 이름 설정
    fun setName(name: String) {
        _nameState.value = name
    }

    // 연락처 설정
    fun setContact(contact: String) {
        _contactState.value = contact
    }

    fun requestUpdate(callback: (UpdateInfoResult) -> Unit) {
        val name = nameState.value
        val contact = contactState.value

        viewModelScope.launch {
            val result = memberUseCase.requestUpdateUserInfo(name, contact)
            callback(result)
        }
    }

    // 이름 유효성 검사
    private fun isValidateName() {
        /** 정규 표현식 내용
         * 한글 2글자 ~ 4글자
         */
        val namePattern = "^[가-힣]{2,4}$"
        val isValidate = Pattern.matches(namePattern, nameState.value.trim())
        _isValidateName.value = isValidate
        Log.d(TAG, "이름 입력값: ${nameState.value}")
        Log.d(TAG, "이름 유효성 검사 결과: ${isValidateName.value}")
    }

    // 연락처 유효성 검사
    private fun isValidateContact() {
        /** 정규 표현식 내용
         *  010-XXXX-XXXX
         */
        val contactPattern = "^01[01]\\d{8}$"
        val isValidate = Pattern.matches(contactPattern, contactState.value.trim())
        _isValidateContact.value = isValidate
        Log.d(TAG, "연락처 입력값: ${contactState.value}")
        Log.d(TAG, "연락처 유효성 검사 결과: ${isValidateContact.value}")
    }

    companion object {
        private const val TAG = "SignUpViewModel"
    }

}