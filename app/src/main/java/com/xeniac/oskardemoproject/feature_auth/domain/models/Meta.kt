package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.MetaDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meta(
    val label: Label?
) : Parcelable {
    fun toMetaDto(): MetaDto = MetaDto(
        labelDto = label?.toLabelDto()
    )
}