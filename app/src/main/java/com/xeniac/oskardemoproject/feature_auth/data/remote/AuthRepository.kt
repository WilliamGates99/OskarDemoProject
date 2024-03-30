package com.xeniac.oskardemoproject.feature_auth.data.remote

import com.xeniac.oskardemoproject.BuildConfig
import com.xeniac.oskardemoproject.core.domain.states.CustomTextFieldState
import com.xeniac.oskardemoproject.core.util.Resource
import com.xeniac.oskardemoproject.feature_auth.domain.models.GetLoginFlowResponse
import com.xeniac.oskardemoproject.feature_auth.domain.models.GetRegistrationFlowResponse

interface AuthRepository {

    suspend fun getLoginFlow(): Resource<GetLoginFlowResponse>

    suspend fun submitLogin(
        flowId: String,
        textFieldsMap: Map<String, CustomTextFieldState>
    ): Resource<Nothing>

    suspend fun getRegistrationFlow(): Resource<GetRegistrationFlowResponse>

    suspend fun submitRegistration(
        flowId: String,
        textFieldsMap: Map<String, CustomTextFieldState>
    ): Resource<Nothing>

    sealed class EndPoints(val url: String) {
        data object GetLoginFlow : EndPoints(
            url = "${BuildConfig.KTOR_HTTP_BASE_URL}/self-service/login/api"
        )

        data object SubmitLogin : EndPoints(
            url = "${BuildConfig.KTOR_HTTP_BASE_URL}/self-service/login"
        )

        data object GetRegistrationFlow : EndPoints(
            url = "${BuildConfig.KTOR_HTTP_BASE_URL}/self-service/registration/api"
        )

        data object SubmitRegistration : EndPoints(
            url = "${BuildConfig.KTOR_HTTP_BASE_URL}/self-service/registration"
        )
    }
}