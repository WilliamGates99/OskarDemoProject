package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.GetLoginFlowResponseDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetLoginFlowResponse(
    val id: String,
    val type: String,
    val ui: Ui,
    val requestUrl: String,
    val state: String,
    val refresh: Boolean,
    val requestedAal: String,
    val organizationId: String?,
    val issuedAt: String,
    val expiresAt: String,
    val createdAt: String,
    val updatedAt: String
) : Parcelable {
    fun toGetLoginFlowResponseDto(): GetLoginFlowResponseDto = GetLoginFlowResponseDto(
        id = id,
        type = type,
        uiDto = ui.toUiDto(),
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