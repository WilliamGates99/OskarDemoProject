package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.Ui
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UiDto(
    @SerialName("action")
    val actionUrl: String,
    @SerialName("method")
    val method: String,
    @SerialName("nodes")
    val nodeDtos: List<NodeDto>,
    @SerialName("messages")
    val messageDtos: List<MessageDto>?
) {
    fun toUi(): Ui = Ui(
        actionUrl = actionUrl,
        method = method,
        nodes = nodeDtos.map { it.toNode() },
        messages = messageDtos?.map { it.toMessage() }
    )
}