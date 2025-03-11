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

    // 이메일
    private val _emailState = MutableStateFlow("")
    val emailState: StateFlow<String> = _emailState

    // 비밀번호
    private val _passwordState = MutableStateFlow("")
    val passwordState: StateFlow<String> = _passwordState

    // 비밀번호 확인 상태
    private val _matchingPasswordState = MutableStateFlow("")
    val matchingPasswordState: StateFlow<String> = _matchingPasswordState

    // 이름 유효성
    private val _isValidateName = MutableStateFlow(false)
    val isValidateName: StateFlow<Boolean> = _isValidateName

    // 연락처 유효성
    private val _isValidateContact = MutableStateFlow(false)
    val isValidateContact: StateFlow<Boolean> = _isValidateContact

    // 이메일 유효성
    private val _isValidateEmail = MutableStateFlow(false)
    val isValidateEmail: StateFlow<Boolean> = _isValidateEmail

    // 비밀번호 유효성
    private val _isValidatePassword = MutableStateFlow(false)
    val isValidatePassword: StateFlow<Boolean> = _isValidatePassword

    // 비밀번호 확인 여부
    private val _isMatchingPassword = MutableStateFlow(false)
    val isMatchingPassword: StateFlow<Boolean> = _isMatchingPassword

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
                .collect { contact ->
                    isValidateContact()
                }
        }
        // 이메일 유효성 검사
        viewModelScope.launch {
            _emailState.debounce(300)
                .collect{ email ->
                    isValidateEmail()

                }
        }

        // 비밀번호 유효성 검사
        viewModelScope.launch {
            _passwordState
                .debounce(300)
                .collect{ password: String ->
                    // password 유효성 검사
                    isValidatePassword()
                }
        }

        // 비밀번호 확인 검사
        viewModelScope.launch {
            _matchingPasswordState
                .collect{ matchingPassword: String ->
                    // paasword 일치 검사
                    isMatchingPassword()
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

    // 이메일 설정
    fun setEmail(email: String) {
        _emailState.value = email
    }

    // 비밀번호 설정
    fun setPassword(password: String){
        _passwordState.value = password
    }

    // 비밀번호 확인 설정
    fun setMatchingPassword(matchingPassword: String){
        _matchingPasswordState.value = matchingPassword
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

    // 이메일 유효성 검사
    private fun isValidateEmail() {
        /** 정규 표현식 내용
         * a-z, A-Z, 0-9까지의 영문자나 숫자 4개 이상
         * _, 소문자, 숫자, 하이픈 중 하나가 반복 가능
         * @ 뒤에 소문자, 대문자, 숫자 중 하나 이상 반복
         * .이 나온 후 소문자, 대문자, 숫자 중 하나 이상 반복
         */
        val emailPattern = "[a-zA-Z0-9]{4,}+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"
        val isValidate = Pattern.matches(emailPattern, emailState.value.trim())
        _isValidateEmail.value = isValidate
        Log.d(TAG, "이메일 유효성 검사 결과 : $isValidate")
    }

    // 비밀번호 유효성 검사
    private fun isValidatePassword(){
        /** 정규 표현식 내용
         * 소문자, 대문자, 특수문자의 조합으로 8글자 이상 20글자 이하
         */
        val pwPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@!%*#?&])[A-Za-z0-9$@!%*#?&]{8,20}$"
        val isValidate = Pattern.matches(pwPattern, passwordState.value.trim())
        _isValidatePassword.value = isValidate
        Log.d(TAG, "비밀번호 유효성 검사 결과 : $isValidate")
    }

    // 비밀번호 확인 일치 여부
    private fun isMatchingPassword(){
        _isMatchingPassword.value = if (matchingPasswordState.value.isNotBlank()) {
            passwordState.value == matchingPasswordState.value
        } else false
        Log.d(TAG, "입력된 비밀번호 확인 : ${matchingPasswordState.value}")
        Log.d(TAG, "비밀번호 확인 여부 검사 결과: ${isMatchingPassword.value}")
    }

    companion object {
        private const val TAG = "SignUpViewModel"
    }

}