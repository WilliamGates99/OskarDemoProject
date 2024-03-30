package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.IdentityDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Identity(
    val id: String,
    val schemaId: String,
    val schemaUrl: String,
    val state: String,
    val traits: Traits,
    val verifiableAddresses: List<VerifiableAddress>,
    val recoveryAddresses: List<RecoveryAddress>,
    val metadataPublic: String?,
    val organizationId: String?,
    val stateChangedAt: String,
    val createdAt: String,
    val updatedAt: String
) : Parcelable {
    fun toIdentityDto(): IdentityDto = IdentityDto(
        id = id,
        schemaId = schemaId,
        schemaUrl = schemaUrl,
        state = state,
        traitsDto = traits.toTraitsDto(),
        verifiableAddressDtos = verifiableAddresses.map { it.toVerifiableAddressDto() },
        recoveryAddressDtos = recoveryAddresses.map { it.toRecoveryAddressDto() },
        metadataPublic = metadataPublic,
        organizationId = organizationId,
        stateChangedAt = stateChangedAt,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}