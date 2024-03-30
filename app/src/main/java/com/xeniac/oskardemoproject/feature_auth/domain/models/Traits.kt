package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.TraitsDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Traits(
    val email: String,
    val fullName: String
) : Parcelable {
    fun toTraitsDto(): TraitsDto = TraitsDto(
        email = email,
        fullName = fullName
    )
}