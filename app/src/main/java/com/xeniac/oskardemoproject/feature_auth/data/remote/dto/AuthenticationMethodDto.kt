package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.AuthenticationMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationMethodDto(
    @SerialName("method")
    val method: String,
    @SerialName("aal")
    val aal: String,
    @SerialName("completed_at")
    val completedAt: String
) {
    fun toAuthenticationMethod(): AuthenticationMethod = AuthenticationMethod(
        method = method,
        aal = aal,
        completedAt = completedAt
    )
}