package com.xeniac.oskardemoproject.feature_auth.data.remote

import com.xeniac.oskardemoproject.R
import com.xeniac.oskardemoproject.core.data.local.PreferencesRepository
import com.xeniac.oskardemoproject.core.domain.states.CustomTextFieldState
import com.xeniac.oskardemoproject.core.util.Resource
import com.xeniac.oskardemoproject.core.util.UiText
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.GetLoginFlowResponseDto
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.GetRegistrationFlowResponseDto
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.LoginErrorResponseDto
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.LoginSuccessResponseDto
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.RegisterErrorResponseDto
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.RegisterSuccessResponseDto
import com.xeniac.oskardemoproject.feature_auth.domain.models.GetLoginFlowResponse
import com.xeniac.oskardemoproject.feature_auth.domain.models.GetRegistrationFlowResponse
import dagger.Lazy
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val preferencesRepository: Lazy<PreferencesRepository>
) : AuthRepository {

    override suspend fun getLoginFlow(): Resource<GetLoginFlowResponse> = try {
        val response = httpClient.get(
            urlString = AuthRepository.EndPoints.GetLoginFlow.url
        ) {
            contentType(ContentType.Application.Json)
        }

        Timber.i("Get login flow response call = ${response.request.call}")

        when (response.status.value) {
            HttpStatusCode.OK.value -> { // Code: 200
                val getLoginFlowResponse = response
                    .body<GetLoginFlowResponseDto>()
                    .toGetLoginFlowResponse()
                Resource.Success(getLoginFlowResponse)
            }
            else -> {
                Resource.Error(UiText.StringResource(R.string.error_something_went_wrong))
            }
        }
    } catch (e: UnresolvedAddressException) { // When device is offline
        Timber.e("Get login flow failed: ${e.message}")
        Resource.Error(UiText.StringResource(R.string.error_network_connection_unavailable))
    } catch (e: Exception) {
        val message = e.message
        Timber.e("Get login flow failed: $message")
        if (message?.contains("timeout") == true) {
            Resource.Error(UiText.StringResource(R.string.error_network_timeout))
        } else if (message?.lowercase(Locale.US)?.contains("unable to resolve host") == true) {
            Resource.Error(UiText.StringResource(R.string.error_network_connection_unavailable))
        } else {
            Resource.Error(UiText.DynamicString(message.toString()))
        }
    }

    override suspend fun submitLogin(
        flowId: String,
        textFieldsMap: Map<String, CustomTextFieldState>
    ): Resource<Nothing> = try {
        val response = httpClient.post(
            urlString = AuthRepository.EndPoints.SubmitLogin.url
        ) {
            contentType(ContentType.Application.Json)

            parameter("flow", flowId)

            var requestBody = "{\"csrf_token\": \"\", \"method\": \"password\",\n"
            textFieldsMap.forEach { (identifier, textFieldState) ->
                requestBody += "\"$identifier\": \"${textFieldState.text}\",\n"
            }
            requestBody += "}"

            val jsonRequestBody = Json.parseToJsonElement(requestBody)
            setBody(
                TextContent(
                    text = jsonRequestBody.toString(),
                    contentType = ContentType.Application.Json
                )
            )
        }

        Timber.i("Submit login response call = ${response.request.call}")

        when (response.status.value) {
            HttpStatusCode.OK.value -> { // Code: 200
                val loginSuccessResponse = response
                    .body<LoginSuccessResponseDto>()
                    .toLoginSuccessResponse()

                preferencesRepository.get().apply {
                    isUserLoggedIn(isLoggedIn = true)
                    setUserToken(token = loginSuccessResponse.sessionToken)
                }

                Resource.Success()
            }
            HttpStatusCode.BadRequest.value -> { // Code: 400
                val loginErrorResponse = response
                    .body<LoginErrorResponseDto>()
                    .toLoginErrorResponse()

                val ui = loginErrorResponse.ui
                val uiMessages = ui.messages

                if (!uiMessages.isNullOrEmpty()) {
                    Resource.Error(UiText.DynamicString(uiMessages[0].text))
                } else {
                    var nodeErrorMessage: String? = null
                    val nodes = ui.nodes
                    nodes.forEach { node ->
                        if (node.messages.isNotEmpty()) {
                            nodeErrorMessage = node.messages[0].text
                        }
                    }

                    nodeErrorMessage?.let { message ->
                        Resource.Error(UiText.DynamicString(message))
                    } ?: Resource.Error(UiText.StringResource(R.string.error_something_went_wrong))
                }
            }
            else -> {
                Resource.Error(UiText.StringResource(R.string.error_something_went_wrong))
            }
        }
    } catch (e: UnresolvedAddressException) { // When device is offline
        Timber.e("Submit login failed: ${e.message}")
        Resource.Error(UiText.StringResource(R.string.error_network_connection_unavailable))
    } catch (e: Exception) {
        val message = e.message
        Timber.e("Submit login failed: $message")
        if (message?.contains("timeout") == true) {
            Resource.Error(UiText.StringResource(R.string.error_network_timeout))
        } else if (message?.lowercase(Locale.US)?.contains("unable to resolve host") == true) {
            Resource.Error(UiText.StringResource(R.string.error_network_connection_unavailable))
        } else {
            Resource.Error(UiText.DynamicString(message.toString()))
        }
    }

    override suspend fun getRegistrationFlow(): Resource<GetRegistrationFlowResponse> = try {
        val response = httpClient.get(
            urlString = AuthRepository.EndPoints.GetRegistrationFlow.url
        ) {
            contentType(ContentType.Application.Json)
        }

        Timber.i("Get register flow response call = ${response.request.call}")

        when (response.status.value) {
            HttpStatusCode.OK.value -> { // Code: 200
                val getRegistrationFlowResponse = response
                    .body<GetRegistrationFlowResponseDto>()
                    .toGetRegistrationFlowResponse()
                Resource.Success(getRegistrationFlowResponse)
            }
            else -> {
                Resource.Error(UiText.StringResource(R.string.error_something_went_wrong))
            }
        }
    } catch (e: UnresolvedAddressException) { // When device is offline
        Timber.e("Get register flow failed: ${e.message}")
        Resource.Error(UiText.StringResource(R.string.error_network_connection_unavailable))
    } catch (e: Exception) {
        val message = e.message
        Timber.e("Get register flow failed: $message")
        if (message?.contains("timeout") == true) {
            Resource.Error(UiText.StringResource(R.string.error_network_timeout))
        } else if (message?.lowercase(Locale.US)?.contains("unable to resolve host") == true) {
            Resource.Error(UiText.StringResource(R.string.error_network_connection_unavailable))
        } else {
            Resource.Error(UiText.DynamicString(message.toString()))
        }
    }

    override suspend fun submitRegistration(
        flowId: String,
        textFieldsMap: Map<String, CustomTextFieldState>
    ): Resource<Nothing> = try {
        val response = httpClient.post(
            urlString = AuthRepository.EndPoints.SubmitRegistration.url
        ) {
            contentType(ContentType.Application.Json)

            parameter("flow", flowId)

            var requestBody = "{\"csrf_token\": \"\", \"method\": \"password\",\n"

            val traitsPrefix = "traits."
            textFieldsMap.forEach { (identifier, textFieldState) ->
                if (!identifier.contains(traitsPrefix)) {
                    requestBody += "\"$identifier\": \"${textFieldState.text}\",\n"
                }
            }

            requestBody += "\"traits\": {\n"
            textFieldsMap.forEach { (identifier, textFieldState) ->
                if (identifier.contains(traitsPrefix)) {
                    requestBody += "\"${identifier.removePrefix(traitsPrefix)}\": \"${textFieldState.text}\",\n"
                }
            }
            requestBody += "}}"

            val jsonRequestBody = Json.parseToJsonElement(requestBody)
            setBody(
                TextContent(
                    text = jsonRequestBody.toString(),
                    contentType = ContentType.Application.Json
                )
            )
        }

        Timber.i("Submit registration response call = ${response.request.call}")

        when (response.status.value) {
            HttpStatusCode.OK.value -> { // Code: 200
                val registerSuccessResponse = response
                    .body<RegisterSuccessResponseDto>()
                    .toRegisterSuccessResponse()

                preferencesRepository.get().apply {
                    isUserLoggedIn(isLoggedIn = true)
                    setUserToken(token = registerSuccessResponse.sessionToken)
                }

                Resource.Success()
            }
            HttpStatusCode.BadRequest.value -> { // Code: 400
                val registerErrorResponse = response
                    .body<RegisterErrorResponseDto>()
                    .toRegisterErrorResponse()

                val ui = registerErrorResponse.ui
                val uiMessages = ui.messages

                if (!uiMessages.isNullOrEmpty()) {
                    Resource.Error(UiText.DynamicString(uiMessages[0].text))
                } else {
                    var nodeErrorMessage: String? = null
                    val nodes = ui.nodes
                    nodes.forEach { node ->
                        if (node.messages.isNotEmpty()) {
                            nodeErrorMessage = node.messages[0].text
                        }
                    }

                    nodeErrorMessage?.let { message ->
                        Resource.Error(UiText.DynamicString(message))
                    } ?: Resource.Error(UiText.StringResource(R.string.error_something_went_wrong))
                }
            }
            else -> {
                Resource.Error(UiText.StringResource(R.string.error_something_went_wrong))
            }
        }
    } catch (e: UnresolvedAddressException) { // When device is offline
        Timber.e("Submit registration failed: ${e.message}")
        Resource.Error(UiText.StringResource(R.string.error_network_connection_unavailable))
    } catch (e: Exception) {
        val message = e.message
        Timber.e("Submit registration failed: $message")
        if (message?.contains("timeout") == true) {
            Resource.Error(UiText.StringResource(R.string.error_network_timeout))
        } else if (message?.lowercase(Locale.US)?.contains("unable to resolve host") == true) {
            Resource.Error(UiText.StringResource(R.string.error_network_connection_unavailable))
        } else {
            Resource.Error(UiText.DynamicString(message.toString()))
        }
    }
}