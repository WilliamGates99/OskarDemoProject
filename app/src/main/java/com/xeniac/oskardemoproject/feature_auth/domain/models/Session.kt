package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.SessionDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Session(
    val id: String,
    val active: Boolean,
    val authenticatorAssuranceLevel: String,
    val authenticationMethods: List<AuthenticationMethod>,
    val identity: Identity,
    val devices: List<Device>,
    val issuedAt: String,
    val authenticatedAt: String,
    val expiresAt: String
) : Parcelable {
    fun toSessionDto(): SessionDto = SessionDto(
        id = id,
        active = active,
        authenticatorAssuranceLevel = authenticatorAssuranceLevel,
        authenticationMethodDtos = authenticationMethods.map { it.toAuthenticationMethodDto() },
        identityDto = identity.toIdentityDto(),
        deviceDtos = devices.map { it.toDeviceDto() },
        issuedAt = issuedAt,
        authenticatedAt = authenticatedAt,
        expiresAt = expiresAt
    )
}