package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.LoginErrorResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginErrorResponseDto(
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
    @SerialName("active")
    val active: String? = null,
    @SerialName("refresh")
    val refresh: Boolean,
    @SerialName("requested_aal")
    val requestedAal: String,
    @SerialName("organization_id")
    val organizationId: String?,
    @SerialName("issued_at")
    val issuedAt: String,
    @SerialName("expires_at")
    val expiresAt: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
) {
    fun toLoginErrorResponse(): LoginErrorResponse = LoginErrorResponse(
        id = id,
        type = type,
        ui = uiDto.toUi(),
        requestUrl = requestUrl,
        state = state,
        active = active,
        refresh = refresh,
        requestedAal = requestedAal,
        organizationId = organizationId,
        issuedAt = issuedAt,
        expiresAt = expiresAt,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}