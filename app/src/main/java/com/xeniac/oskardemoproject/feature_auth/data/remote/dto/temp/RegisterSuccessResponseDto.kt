package com.xeniac.oskardemoproject.feature_auth.data.remote.dto.temp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterSuccessResponseDto(
    @SerialName("session_token")
    val sessionToken: String,
    @SerialName("session")
    val sessionDto: SessionDto,
    @SerialName("identity")
    val identityDto: IdentityDto,
    @SerialName("continue_with")
    val continueWithDtos: List<ContinueWithDto>
)