package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.RecoveryAddress
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecoveryAddressDto(
    @SerialName("id")
    val id: String,
    @SerialName("value")
    val value: String,
    @SerialName("via")
    val via: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
) {
    fun toRecoveryAddress(): RecoveryAddress = RecoveryAddress(
        id = id,
        value = value,
        via = via,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}