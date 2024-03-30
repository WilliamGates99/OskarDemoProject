package com.xeniac.oskardemoproject.feature_auth.data.remote.dto

import com.xeniac.oskardemoproject.feature_auth.domain.models.Device
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceDto(
    @SerialName("id")
    val id: String,
    @SerialName("ip_address")
    val ipAddress: String,
    @SerialName("user_agent")
    val userAgent: String,
    @SerialName("location")
    val location: String
) {
    fun toDevice(): Device = Device(
        id = id,
        ipAddress = ipAddress,
        userAgent = userAgent,
        location = location
    )
}