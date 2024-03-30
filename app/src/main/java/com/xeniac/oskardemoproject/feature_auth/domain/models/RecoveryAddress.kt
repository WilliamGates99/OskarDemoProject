package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.RecoveryAddressDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecoveryAddress(
    val id: String,
    val value: String,
    val via: String,
    val createdAt: String,
    val updatedAt: String
) : Parcelable {
    fun toRecoveryAddressDto(): RecoveryAddressDto = RecoveryAddressDto(
        id = id,
        value = value,
        via = via,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}