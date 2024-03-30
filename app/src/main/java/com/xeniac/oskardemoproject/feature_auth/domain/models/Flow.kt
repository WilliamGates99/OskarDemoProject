package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.FlowDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Flow(
    val id: String,
    val verifiableAddress: String
) : Parcelable {
    fun toFlowDto(): FlowDto = FlowDto(
        id = id,
        verifiableAddress = verifiableAddress
    )
}