package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.GetRegistrationFlowResponseDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetRegistrationFlowResponse(
    val id: String,
    val type: String,
    val ui: Ui,
    val requestUrl: String,
    val state: String,
    val organizationId: String?,
    val issuedAt: String,
    val expiresAt: String
) : Parcelable {
    fun toGetRegistrationFlowResponseDto(): GetRegistrationFlowResponseDto =
        GetRegistrationFlowResponseDto(
            id = id,
            type = type,
            uiDto = ui.toUiDto(),
            requestUrl = requestUrl,
            state = state,
            organizationId = organizationId,
            issuedAt = issuedAt,
            expiresAt = expiresAt,
        )
}