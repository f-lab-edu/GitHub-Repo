package com.prac.data.source.model

import com.prac.data.repository.dto.OwnerDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OwnerModel(
    @SerialName("login") val login: String,
    @SerialName("avatar_url") val avatarUrl: String
) {
    fun toDto() =
        OwnerDto(login, avatarUrl)
}