package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.LoginSuccessResponseDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginSuccessResponse(
    val sessionToken: String,
    val session: Session,
    val continueWiths: List<ContinueWith>? = null
) : Parcelable {
    fun toLoginSuccessResponseDto(): LoginSuccessResponseDto = LoginSuccessResponseDto(
        sessionToken = sessionToken,
        sessionDto = session.toSessionDto(),
        continueWithDtos = continueWiths?.map { it.toContinueWithDto() }
    )
}