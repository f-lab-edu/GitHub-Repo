package com.prac.data.source.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RepoDto(
    @SerialName("id") val id: Int = 0,
    @SerialName("name") val name: String = "",
    @SerialName("owner") val owner: OwnerDto = OwnerDto(),
    @SerialName("stargazers_count") val stargazersCount: Int = 0,
    @SerialName("updated_at") val updatedAt: String = ""
)