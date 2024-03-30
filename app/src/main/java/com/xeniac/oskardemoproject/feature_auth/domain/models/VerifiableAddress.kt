package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.VerifiableAddressDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class VerifiableAddress(
    val id: String,
    val value: String,
    val verified: Boolean,
    val via: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String
) : Parcelable {
    fun toVerifiableAddressDto(): VerifiableAddressDto = VerifiableAddressDto(
        id = id,
        value = value,
        verified = verified,
        via = via,
        status = status,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}