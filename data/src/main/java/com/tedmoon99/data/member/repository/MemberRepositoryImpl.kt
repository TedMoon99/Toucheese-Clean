package com.tedmoon99.data.member.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tedmoon99.data.member.datasource.MemberService
import com.tedmoon99.data.member.mapper.AdditionalInfoMapper
import com.tedmoon99.data.member.mapper.SignInMapper
import com.tedmoon99.data.member.mapper.SignUpMapper
import com.tedmoon99.data.member.model.SignInResponse
import com.tedmoon99.domain.member.model.AdditionalInfoEntity
import com.tedmoon99.domain.member.model.SignInRequestEntity
import com.tedmoon99.domain.member.model.SignInResult
import com.tedmoon99.domain.member.model.SignOutResult
import com.tedmoon99.domain.member.model.SignUpRequestEntity
import com.tedmoon99.domain.member.model.SignUpResult
import com.tedmoon99.domain.member.model.UpdateInfoResult
import com.tedmoon99.domain.member.repository.MemberRepository
import com.tedmoon99.domain.member.repository.TokenRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val memberService: MemberService,
    private val dataStore: DataStore<Preferences>,
): MemberRepository {



    init {
        runBlocking{ initData() }
    }

    override suspend fun requestSignIn(request: SignInRequestEntity): SignInResult {
        try {
            val response = memberService.requestSignIn(SignInMapper.fromDomain(request))
            // 요청 성공 시
            if (response.isSuccessful) {

                val header = response.headers()["Authorization"]
                if (header != null) {
                    val token = header.removePrefix("Bearer ")
                    val body: SignInResponse? = response.body()
                    if (body != null) {
                        // 토큰 저장
                        tokenRepository.setAccessToken(token)
                        tokenRepository.setRefreshToken(body.refreshToken)
                        tokenRepository.setDeviceId(body.deviceId)

                        // 회원 정보 (이름, 이메일, Id) 저장
                        setUserId(body.memberId)
                        setUserName(body.name)
                        setUserEmail(body.name)
                    }

                }
                return SignInResult.Success("로그인 성공")
            }
            return SignInResult.Failure("로그인 실패")
        } catch (error: Exception) {
            Log.e(TAG, "로그인 에러: ${error.message}")
            return SignInResult.NetworkError
        }
    }

    override suspend fun requestSignUp(request: SignUpRequestEntity): SignUpResult {
        val dto = SignUpMapper.fromDomain(request)
        val response = memberService.requestSignUp(dto)
        Log.d(TAG, "회원가입 요청 결과: ${response.code()}")
        return SignUpResult(response.isSuccessful && response.code() == 200)
    }

    override suspend fun requestSignOut(): SignOutResult {
        val deviceId = tokenRepository.getDeviceId()
        val response = memberService.requestLogout(deviceId)
        return if (response.code() == 200) {
            tokenRepository.deleteTokens()
            SignOutResult.Success("로그아웃 완료")
        } else {
            SignOutResult.Failure("로그아웃 실패")
        }
    }

    override suspend fun requestUpdateUserInfo(request: AdditionalInfoEntity): UpdateInfoResult {
        val additionalInfoDto = AdditionalInfoMapper.fromDomain(request)
        val response = memberService.updateUserInfo( additionalInfoDto)

        return if (response.isSuccessful && response.code() == 200) {
            UpdateInfoResult(true)
        } else {
            UpdateInfoResult(false, response.errorBody().toString())
        }
    }

    override suspend fun setUserId(userId: Int) {
        dataStore.edit { prefs ->
            prefs[MEMBER_ID_KEY] = userId
        }
    }

    override suspend fun getUserId(): Int? {
        return dataStore.data.first()[MEMBER_ID_KEY]
    }

    override suspend fun setUserEmail(email: String) {
        dataStore.edit { prefs ->
            prefs[MEMBER_EMAIL_KEY] = email
        }
    }

    override suspend fun getUserEmail(): String? {
        return dataStore.data.first()[MEMBER_EMAIL_KEY]
    }

    override suspend fun setUserName(userName: String) {
        dataStore.edit { prefs ->
            prefs[MEMBER_NAME_KEY] = userName
        }
    }

    override suspend fun getUserName(): String? {
        return dataStore.data.first()[MEMBER_NAME_KEY]
    }

    private suspend fun initData() {
        // 기존 정보 제거
        tokenRepository.deleteTokens()
        dataStore.edit {
            it.remove(MEMBER_ID_KEY)
            it.remove(MEMBER_NAME_KEY)
            it.remove(MEMBER_EMAIL_KEY)
            it.remove(DEVICE_ID_KEY)
        }
    }

    companion object {
        private val TAG = "SignInRepositoryImpl"
        private val MEMBER_ID_KEY = intPreferencesKey("memberId")
        private val MEMBER_NAME_KEY = stringPreferencesKey("memberName")
        private val MEMBER_EMAIL_KEY = stringPreferencesKey("memberEmail")
        private val DEVICE_ID_KEY = stringPreferencesKey("deviceId")
    }
}