package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.AuthenticationMethodDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthenticationMethod(
    val method: String,
    val aal: String,
    val completedAt: String
) : Parcelable {
    fun toAuthenticationMethodDto(): AuthenticationMethodDto = AuthenticationMethodDto(
        method = method,
        aal = aal,
        completedAt = completedAt
    )
}