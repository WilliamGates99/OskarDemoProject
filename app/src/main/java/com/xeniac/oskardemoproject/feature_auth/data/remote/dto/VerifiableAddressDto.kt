package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.VerifiableAddress
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifiableAddressDto(
    @SerialName("id")
    val id: String,
    @SerialName("value")
    val value: String,
    @SerialName("verified")
    val verified: Boolean,
    @SerialName("via")
    val via: String,
    @SerialName("status")
    val status: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
) {
    fun toVerifiableAddress(): VerifiableAddress = VerifiableAddress(
        id = id,
        value = value,
        verified = verified,
        via = via,
        status = status,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}