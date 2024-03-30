package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.UiDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ui(
    val actionUrl: String,
    val method: String,
    val nodes: List<Node>,
    val messages: List<Message>?
) : Parcelable {
    fun toUiDto(): UiDto = UiDto(
        actionUrl = actionUrl,
        method = method,
        nodeDtos = nodes.map { it.toNodeDto() },
        messageDtos = messages?.map { it.toMessageDto() }
    )
}