package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.ContinueWithDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContinueWith(
    val action: String,
    val flow: Flow?,
    val orySessionToken: String?
) : Parcelable {
    fun toContinueWithDto(): ContinueWithDto = ContinueWithDto(
        action = action,
        flowDto = flow?.toFlowDto(),
        orySessionToken = orySessionToken
    )
}