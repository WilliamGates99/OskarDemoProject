package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.Session
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionDto(
    @SerialName("id")
    val id: String,
    @SerialName("active")
    val active: Boolean,
    @SerialName("authenticator_assurance_level")
    val authenticatorAssuranceLevel: String,
    @SerialName("authentication_methods")
    val authenticationMethodDtos: List<AuthenticationMethodDto>,
    @SerialName("identity")
    val identityDto: IdentityDto,
    @SerialName("devices")
    val deviceDtos: List<DeviceDto>,
    @SerialName("issued_at")
    val issuedAt: String,
    @SerialName("authenticated_at")
    val authenticatedAt: String,
    @SerialName("expires_at")
    val expiresAt: String
) {
    fun toSession(): Session = Session(
        id = id,
        active = active,
        authenticatorAssuranceLevel = authenticatorAssuranceLevel,
        authenticationMethods = authenticationMethodDtos.map { it.toAuthenticationMethod() },
        identity = identityDto.toIdentity(),
        devices = deviceDtos.map { it.toDevice() },
        issuedAt = issuedAt,
        authenticatedAt = authenticatedAt,
        expiresAt = expiresAt
    )
}