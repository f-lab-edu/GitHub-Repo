package com.prac.data.source.dto

import com.prac.data.repository.model.RepoModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RepoDto(
    @SerialName("id") val id: Int = 0,
    @SerialName("name") val name: String = "",
    @SerialName("owner") val owner: OwnerDto = OwnerDto(),
    @SerialName("stargazers_count") val stargazersCount: Int = 0,
    @SerialName("updated_at") val updatedAt: String = "",
    val isStarred: Boolean = false
) {
    fun toModel() =
        RepoModel(id, name, owner.toModel(), stargazersCount, updatedAt, isStarred)
}