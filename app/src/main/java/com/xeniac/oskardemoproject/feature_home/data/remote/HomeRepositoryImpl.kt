package com.xeniac.oskardemoproject.feature_home.data.remote

import com.xeniac.oskardemoproject.R
import com.xeniac.oskardemoproject.core.data.local.PreferencesRepository
import com.xeniac.oskardemoproject.core.util.Resource
import com.xeniac.oskardemoproject.core.util.UiText
import dagger.Lazy
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val preferencesRepository: Lazy<PreferencesRepository>
) : HomeRepository {

    override suspend fun getUserToken(): Resource<UserToken> = try {
        Resource.Success(preferencesRepository.get().getUserToken())
    } catch (e: Exception) {
        e.printStackTrace()
        Resource.Error(UiText.StringResource(R.string.error_something_went_wrong))
    }

    override suspend fun logout(): Resource<Nothing> = try {
        preferencesRepository.get().isUserLoggedIn(false)
        preferencesRepository.get().removeUserToken()
        Resource.Success()
    } catch (e: Exception) {
        e.printStackTrace()
        Resource.Error(UiText.StringResource(R.string.error_something_went_wrong))
    }
}