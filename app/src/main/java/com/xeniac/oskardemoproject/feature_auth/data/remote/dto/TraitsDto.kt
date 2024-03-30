package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.Traits
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TraitsDto(
    @SerialName("email")
    val email: String,
    @SerialName("full_name")
    val fullName: String
) {
    fun toTraits(): Traits = Traits(
        email = email,
        fullName = fullName
    )
}