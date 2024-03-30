package com.xeniac.oskardemoproject.feature_auth.data.remote

import com.xeniac.oskardemoproject.R
import com.xeniac.oskardemoproject.core.util.Resource
import com.xeniac.oskardemoproject.core.util.UiText
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.GetLoginFlowResponseDto
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.GetRegistrationFlowResponseDto
import com.xeniac.oskardemoproject.feature_auth.domain.models.Ui
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
) : AuthRepository {

    override suspend fun getLoginFlow(): Resource<Ui> = try {
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
                Resource.Success(getLoginFlowResponse.ui)
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

    override suspend fun getRegistrationFlow(): Resource<Ui> = try {
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
                Resource.Success(getRegistrationFlowResponse.ui)
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
}