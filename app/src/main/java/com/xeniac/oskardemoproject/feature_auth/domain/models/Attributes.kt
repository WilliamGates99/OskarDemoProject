package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.AttributesDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Attributes(
    val name: String,
    val type: String,
    val value: String?,
    val required: Boolean?,
    val disabled: Boolean,
    val nodeType: String,
    val autocomplete: String?
) : Parcelable {
    fun toAttributesDto(): AttributesDto = AttributesDto(
        name = name,
        type = type,
        value = value,
        required = required,
        disabled = disabled,
        nodeType = nodeType,
        autocomplete = autocomplete
    )
}