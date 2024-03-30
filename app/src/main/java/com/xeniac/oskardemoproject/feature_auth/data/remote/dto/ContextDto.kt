package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.Context
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContextDto(
    @SerialName("title")
    val title: String?,
    @SerialName("property")
    val property: String?,
    @SerialName("available_credential_types")
    val availableCredentialTypes: List<String>?,
    @SerialName("available_oidc_providers")
    val availableOidcProviders: String?,
    @SerialName("credential_identifier_hint")
    val credentialIdentifierHint: String?
) {
    fun toContext(): Context = Context(
        title = title,
        property = property,
        availableCredentialTypes = availableCredentialTypes,
        availableOidcProviders = availableOidcProviders,
        credentialIdentifierHint = credentialIdentifierHint
    )
}