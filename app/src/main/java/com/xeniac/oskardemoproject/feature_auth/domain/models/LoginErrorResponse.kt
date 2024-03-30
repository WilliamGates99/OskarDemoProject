package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.LoginErrorResponseDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginErrorResponse(
    val id: String,
    val type: String,
    val ui: Ui,
    val requestUrl: String,
    val state: String,
    val active: String?,
    val refresh: Boolean,
    val requestedAal: String,
    val organizationId: String?,
    val issuedAt: String,
    val expiresAt: String,
    val createdAt: String,
    val updatedAt: String
) : Parcelable {
    fun toLoginErrorResponseDto(): LoginErrorResponseDto = LoginErrorResponseDto(
        id = id,
        type = type,
        uiDto = ui.toUiDto(),
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