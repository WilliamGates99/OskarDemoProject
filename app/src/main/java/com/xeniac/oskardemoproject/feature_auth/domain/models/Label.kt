package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.LabelDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Label(
    val id: Int,
    val text: String,
    val type: String,
    val context: Context?
) : Parcelable {
    fun toLabelDto(): LabelDto = LabelDto(
        id = id,
        text = text,
        type = type,
        contextDto = context?.toContextDto()
    )
}