package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.GetLoginFlowResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetLoginFlowResponseDto(
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
    @SerialName("refresh")
    val refresh: Boolean,
    @SerialName("requested_aal")
    val requestedAal: String,
    @SerialName("organization_id")
    val organizationId: String? = null,
    @SerialName("issued_at")
    val issuedAt: String,
    @SerialName("expires_at")
    val expiresAt: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
) {
    fun toGetLoginFlowResponse(): GetLoginFlowResponse = GetLoginFlowResponse(
        id = id,
        type = type,
        ui = uiDto.toUi(),
        requestUrl = requestUrl,
        state = state,
        refresh = refresh,
        requestedAal = requestedAal,
        organizationId = organizationId,
        issuedAt = issuedAt,
        expiresAt = expiresAt,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}