package com.xeniac.oskardemoproject.core.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

class PreferencesRepositoryImp @Inject constructor(
    private val settingsDataStore: DataStore<Preferences>
) : PreferencesRepository {

    private object PreferencesKeys {
        val IS_USER_LOGGED_IN = booleanPreferencesKey(name = "isUserLoggedIn")
        val USER_TOKEN = stringPreferencesKey(name = "userToken")
    }

    override suspend fun isUserLoggedIn(): Boolean = try {
        settingsDataStore.data.first()[PreferencesKeys.IS_USER_LOGGED_IN] ?: false
    } catch (e: Exception) {
        Timber.e("isUserLoggedIn Exception: $e")
        false
    }

    override suspend fun getUserToken(): UserToken = try {
        settingsDataStore.data.first()[PreferencesKeys.USER_TOKEN] ?: ""
    } catch (e: Exception) {
        Timber.e("getUserToken Exception: $e")
        ""
    }

    override suspend fun isUserLoggedIn(isLoggedIn: Boolean) {
        try {
            settingsDataStore.edit { preferences ->
                preferences[PreferencesKeys.IS_USER_LOGGED_IN] = isLoggedIn
                Timber.i("isUserLoggedIn edited to $isLoggedIn")
            }
        } catch (e: Exception) {
            Timber.e("isUserLoggedIn Exception: $e")
        }
    }

    override suspend fun setUserToken(token: String) {
        try {
            settingsDataStore.edit { preferences ->
                preferences[PreferencesKeys.USER_TOKEN] = token
                Timber.i("User token edited to $token")
            }
        } catch (e: Exception) {
            Timber.e("setUserToken Exception: $e")
        }
    }

    override suspend fun removeUserToken() {
        try {
            settingsDataStore.edit { preferences ->
                preferences.remove(PreferencesKeys.USER_TOKEN)
                Timber.i("User token removed")
            }
        } catch (e: Exception) {
            Timber.e("removeUserToken Exception: $e")
        }
    }
}