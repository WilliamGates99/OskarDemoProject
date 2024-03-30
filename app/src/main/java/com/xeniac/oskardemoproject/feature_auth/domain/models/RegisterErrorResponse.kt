package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.RegisterErrorResponseDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterErrorResponse(
    val id: String,
    val type: String,
    val ui: Ui,
    val requestUrl: String,
    val state: String,
    val organizationId: String?,
    val issuedAt: String,
    val expiresAt: String
) : Parcelable {
    fun toRegisterErrorResponseDto(): RegisterErrorResponseDto = RegisterErrorResponseDto(
        id = id,
        type = type,
        uiDto = ui.toUiDto(),
        requestUrl = requestUrl,
        state = state,
        organizationId = organizationId,
        issuedAt = issuedAt,
        expiresAt = expiresAt
    )
}