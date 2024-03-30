package com.xeniac.oskardemoproject.feature_home.data.remote

import com.xeniac.oskardemoproject.core.util.Resource

typealias UserToken = String

interface HomeRepository {

    suspend fun getUserToken(): Resource<UserToken>

    suspend fun logout(): Resource<Nothing>
}