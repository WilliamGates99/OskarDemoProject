package com.xeniac.oskardemoproject.feature_auth.data.remote

import com.xeniac.oskardemoproject.BuildConfig
import com.xeniac.oskardemoproject.core.util.Resource
import com.xeniac.oskardemoproject.feature_auth.domain.models.Ui

interface AuthRepository {

    suspend fun getLoginFlow(): Resource<Ui>

//    suspend fun submitLoginFlow(flowId: String): Resource<>

    suspend fun getRegistrationFlow(): Resource<Ui>

    //suspend fun submitRegistrationFlow(flowId: String): Resource<>

    sealed class EndPoints(val url: String) {
        data object GetLoginFlow : EndPoints(
            url = "${BuildConfig.KTOR_HTTP_BASE_URL}/self-service/login/api"
        )

        data object SubmitLoginFlow : EndPoints(
            url = "${BuildConfig.KTOR_HTTP_BASE_URL}/self-service/login"
        )

        data object GetRegistrationFlow : EndPoints(
            url = "${BuildConfig.KTOR_HTTP_BASE_URL}/self-service/registration/api"
        )

        data object SubmitRegistrationFlow : EndPoints(
            url = "${BuildConfig.KTOR_HTTP_BASE_URL}/self-service/registration"
        )
    }
}