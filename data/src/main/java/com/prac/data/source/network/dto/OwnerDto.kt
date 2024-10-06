package com.prac.data.source.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OwnerDto(
    @SerialName("login") val login: String = "",
    @SerialName("avatar_url") val avatarUrl: String = ""
)