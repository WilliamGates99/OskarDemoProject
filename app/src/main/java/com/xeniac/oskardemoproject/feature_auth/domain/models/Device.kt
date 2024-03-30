package com.xeniac.oskardemoproject.feature_auth.domain.models

import android.os.Parcelable
import com.xeniac.oskardemoproject.feature_auth.data.remote.dto.DeviceDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Device(
    val id: String,
    val ipAddress: String,
    val userAgent: String,
    val location: String
) : Parcelable {
    fun toDeviceDto(): DeviceDto = DeviceDto(
        id = id,
        ipAddress = ipAddress,
        userAgent = userAgent,
        location = location
    )
}