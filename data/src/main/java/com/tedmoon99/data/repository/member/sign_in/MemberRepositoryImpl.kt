package com.tedmoon99.data.repository.member.sign_in

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tedmoon99.data.mapper.member.SignInMapper
import com.tedmoon99.data.model.remote.member.sign_in.SignInResponse
import com.tedmoon99.domain.entity.auth.sign_in.SignInRequestEntity
import com.tedmoon99.domain.repository.member.MemberRepository
import com.tedmoon99.domain.repository.member.TokenRepository
import com.tedmoon99.domain.intent.member.SignInResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val signInService: SignInService,
    private val dataStore: DataStore<Preferences>,
): MemberRepository {

    init {
        runBlocking{ initData() }
    }

    override suspend fun requestSignIn(request: SignInRequestEntity): SignInResult {
        try {
            val response = signInService.requestSignIn(SignInMapper.fromDomain(request))
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

                        // 회원 정보 (이름, 이메일, Id) 저장
                        dataStore.edit {
                            it[MEMBER_NAME_KEY] = body.name
                            it[MEMBER_EMAIL_KEY] = body.email
                            it[MEMBER_ID_KEY] = body.memberId
                        }
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

    override suspend fun getUserId(): Int? {
        return dataStore.data.first()[MEMBER_ID_KEY]
    }

    override suspend fun getUserEmail(): String? {
        return dataStore.data.first()[MEMBER_EMAIL_KEY]
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
        }
    }

    companion object {
        private val TAG = "SignInRepositoryImpl"
        private val MEMBER_ID_KEY = intPreferencesKey("memberId")
        private val MEMBER_NAME_KEY = stringPreferencesKey("memberName")
        private val MEMBER_EMAIL_KEY = stringPreferencesKey("memberEmail")

    }
}