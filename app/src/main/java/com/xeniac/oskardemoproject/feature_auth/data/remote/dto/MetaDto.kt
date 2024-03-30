package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.Meta
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetaDto(
    @SerialName("label")
    val labelDto: LabelDto?
) {
    fun toMeta(): Meta = Meta(
        label = labelDto?.toLabel()
    )
}