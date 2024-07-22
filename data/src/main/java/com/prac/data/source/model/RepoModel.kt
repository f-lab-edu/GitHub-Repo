package com.prac.data.source.model

import com.prac.data.repository.dto.RepoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RepoModel(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("owner") val owner: OwnerModel,
    @SerialName("stargazers_count") val stargazersCount: Int,
    @SerialName("updated_at") val updatedAt: String
) {
    fun toDto() =
        RepoDto(id, name, owner.toDto(), stargazersCount, updatedAt)
}