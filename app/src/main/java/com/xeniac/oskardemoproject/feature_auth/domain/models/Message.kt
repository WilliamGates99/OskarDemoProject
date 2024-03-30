package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.MessageDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    val id: Int,
    val text: String,
    val type: String,
    val context: Context?
) : Parcelable {
    fun toMessageDto(): MessageDto = MessageDto(
        id = id,
        text = text,
        type = type,
        contextDto = context?.toContextDto()
    )
}