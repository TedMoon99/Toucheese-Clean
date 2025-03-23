package com.tedmoon99.data.repository.member.token

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tedmoon99.domain.member.repository.TokenRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : TokenRepository {

    override suspend fun setAccessToken(accessToken: String) {
        dataStore.edit { it[ACCESS_TOKEN_KEY] = accessToken }
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        dataStore.edit { it[REFRESH_TOKEN_KEY] = refreshToken }
    }

    override suspend fun setDeviceId(deviceId: String) {
        dataStore.edit { it[DEVICE_ID_KEY] = deviceId }
    }

    override suspend fun getAccessToken(): String? {
        return dataStore.data.first()[ACCESS_TOKEN_KEY]
    }

    override suspend fun getRefreshToken(): String? {
        return dataStore.data.first()[REFRESH_TOKEN_KEY]
    }

    override suspend fun getDeviceId(): String? {
        return dataStore.data.first()[DEVICE_ID_KEY]
    }


    override suspend fun deleteTokens() {
        dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN_KEY)
            prefs.remove(REFRESH_TOKEN_KEY)
        }
    }

    companion object {
        private const val TAG = "TokenRepositoryImpl"
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("accessToken")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refreshToken")
        private val DEVICE_ID_KEY = stringPreferencesKey("deviceId")
    }
}