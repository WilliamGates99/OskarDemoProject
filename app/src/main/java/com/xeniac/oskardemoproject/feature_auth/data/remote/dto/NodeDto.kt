package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.Node
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NodeDto(
    @SerialName("type")
    val type: String,
    @SerialName("group")
    val group: String,
    @SerialName("attributes")
    val attributesDto: AttributesDto,
    @SerialName("messages")
    val messageDtos: List<MessageDto>,
    @SerialName("meta")
    val metaDto: MetaDto?
) {
    fun toNode(): Node = Node(
        type = type,
        group = group,
        attributes = attributesDto.toAttributes(),
        messages = messageDtos.map { it.toMessage() },
        meta = metaDto?.toMeta()
    )
}