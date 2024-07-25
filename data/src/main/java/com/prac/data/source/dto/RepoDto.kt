package com.prac.data.source.dto

import com.prac.data.repository.dto.RepoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RepoDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("owner") val owner: OwnerDto,
    @SerialName("stargazers_count") val stargazersCount: Int,
    @SerialName("updated_at") val updatedAt: String
) {
    fun toModel() =
        RepoDto(id, name, owner.toModel(), stargazersCount, updatedAt)
}