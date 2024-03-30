package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.ContextDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Context(
    val title: String?,
    val property: String?,
    val availableCredentialTypes: List<String>?,
    val availableOidcProviders: String?,
    val credentialIdentifierHint: String?
) : Parcelable {
    fun toContextDto(): ContextDto = ContextDto(
        title = title,
        property = property,
        availableCredentialTypes = availableCredentialTypes,
        availableOidcProviders = availableOidcProviders,
        credentialIdentifierHint = credentialIdentifierHint
    )
}