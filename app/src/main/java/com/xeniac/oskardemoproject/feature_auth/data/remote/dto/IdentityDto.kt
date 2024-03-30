package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.Identity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IdentityDto(
    @SerialName("id")
    val id: String,
    @SerialName("schema_id")
    val schemaId: String,
    @SerialName("schema_url")
    val schemaUrl: String,
    @SerialName("state")
    val state: String,
    @SerialName("traits")
    val traitsDto: TraitsDto,
    @SerialName("verifiable_addresses")
    val verifiableAddressDtos: List<VerifiableAddressDto>,
    @SerialName("recovery_addresses")
    val recoveryAddressDtos: List<RecoveryAddressDto>,
    @SerialName("metadata_public")
    val metadataPublic: String?,
    @SerialName("organization_id")
    val organizationId: String?,
    @SerialName("state_changed_at")
    val stateChangedAt: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
) {
    fun toIdentity(): Identity = Identity(
        id = id,
        schemaId = schemaId,
        schemaUrl = schemaUrl,
        state = state,
        traits = traitsDto.toTraits(),
        verifiableAddresses = verifiableAddressDtos.map { it.toVerifiableAddress() },
        recoveryAddresses = recoveryAddressDtos.map { it.toRecoveryAddress() },
        metadataPublic = metadataPublic,
        organizationId = organizationId,
        stateChangedAt = stateChangedAt,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}