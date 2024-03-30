package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.LoginSuccessResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginSuccessResponseDto(
    @SerialName("session_token")
    val sessionToken: String,
    @SerialName("session")
    val sessionDto: SessionDto,
    @SerialName("continue_with")
    val continueWithDtos: List<ContinueWithDto>? = null
) {
    fun toLoginSuccessResponse(): LoginSuccessResponse = LoginSuccessResponse(
        sessionToken = sessionToken,
        session = sessionDto.toSession(),
        continueWiths = continueWithDtos?.map { it.toContinueWith() }
    )
}