package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.Flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlowDto(
    @SerialName("id")
    val id: String,
    @SerialName("verifiable_address")
    val verifiableAddress: String
) {
    fun toFlow(): Flow = Flow(
        id = id,
        verifiableAddress = verifiableAddress
    )
}