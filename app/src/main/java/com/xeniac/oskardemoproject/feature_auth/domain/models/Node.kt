package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.NodeDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Node(
    val type: String,
    val group: String,
    val attributes: Attributes,
    val messages: List<Message>,
    val meta: Meta?
) : Parcelable {
    fun toNodeDto(): NodeDto = NodeDto(
        type = type,
        group = group,
        attributesDto = attributes.toAttributesDto(),
        messageDtos = messages.map { it.toMessageDto() },
        metaDto = meta?.toMetaDto()
    )
}