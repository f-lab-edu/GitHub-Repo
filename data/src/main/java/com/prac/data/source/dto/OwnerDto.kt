package com.prac.data.source.dto

import com.prac.data.repository.model.OwnerModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OwnerDto(
    @SerialName("login") val login: String = "",
    @SerialName("avatar_url") val avatarUrl: String = ""
) {
    fun toModel() =
        OwnerModel(login, avatarUrl)
}