package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.ContinueWith
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContinueWithDto(
    @SerialName("action")
    val action: String,
    @SerialName("flow")
    val flowDto: FlowDto? = null,
    @SerialName("ory_session_token")
    val orySessionToken: String? = null
) {
    fun toContinueWith(): ContinueWith = ContinueWith(
        action = action,
        flow = flowDto?.toFlow(),
        orySessionToken = orySessionToken
    )
}