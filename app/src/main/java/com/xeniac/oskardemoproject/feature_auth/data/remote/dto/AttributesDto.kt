package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.Attributes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttributesDto(
    @SerialName("name")
    val name: String,
    @SerialName("type")
    val type: String,
    @SerialName("value")
    val value: String? = null,
    @SerialName("required")
    val required: Boolean? = null,
    @SerialName("disabled")
    val disabled: Boolean,
    @SerialName("node_type")
    val nodeType: String,
    @SerialName("autocomplete")
    val autocomplete: String? = null
) {
    fun toAttributes(): Attributes = Attributes(
        name = name,
        type = type,
        value = value,
        required = required,
        disabled = disabled,
        nodeType = nodeType,
        autocomplete = autocomplete
    )
}