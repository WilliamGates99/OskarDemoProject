package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.GetRegistrationFlowResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetRegistrationFlowResponseDto(
    @SerialName("id")
    val id: String,
    @SerialName("type")
    val type: String,
    @SerialName("ui")
    val uiDto: UiDto,
    @SerialName("request_url")
    val requestUrl: String,
    @SerialName("state")
    val state: String,
    @SerialName("organization_id")
    val organizationId: String?,
    @SerialName("issued_at")
    val issuedAt: String,
    @SerialName("expires_at")
    val expiresAt: String
) {
    fun toGetRegistrationFlowResponse(): GetRegistrationFlowResponse = GetRegistrationFlowResponse(
        id = id,
        type = type,
        ui = uiDto.toUi(),
        requestUrl = requestUrl,
        state = state,
        organizationId = organizationId,
        issuedAt = issuedAt,
        expiresAt = expiresAt,
    )
}