package com.xeniac.oskardemoproject.core.data.local

typealias UserToken = String

interface PreferencesRepository {

    suspend fun isUserLoggedIn(): Boolean

    suspend fun getUserToken(): UserToken

    suspend fun isUserLoggedIn(isLoggedIn: Boolean)

    suspend fun setUserToken(token: String)

    suspend fun removeUserToken()
}