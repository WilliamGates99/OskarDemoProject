package com.xeniac.oskardemoproject.feature_auth.domain.models

import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.RegisterSuccessResponseDto

data class RegisterSuccessResponse(
    val sessionToken: String,
    val session: Session,
    val identity: Identity,
    val continueWiths: List<ContinueWith>
) {
    fun toRegisterSuccessResponseDto(): RegisterSuccessResponseDto = RegisterSuccessResponseDto(
        sessionToken = sessionToken,
        sessionDto = session.toSessionDto(),
        identityDto = identity.toIdentityDto(),
        continueWithDtos = continueWiths.map { it.toContinueWithDto() }
    )
}