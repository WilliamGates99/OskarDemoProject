package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.Message
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    @SerialName("id")
    val id: Int,
    @SerialName("text")
    val text: String,
    @SerialName("type")
    val type: String,
    @SerialName("context")
    val contextDto: ContextDto
) {
    fun toMessage(): Message = Message(
        id = id,
        text = text,
        type = type,
        context = contextDto.toContext()
    )
}