package com.prac.data.repository.model

import com.prac.data.di.entity.RepoEntity

internal data class RepoModel(
    val id: Int,
    val name: String,
    val owner: OwnerModel,
    val stargazersCount: Int,
    val updatedAt: String
) {
    fun toEntity() =
        RepoEntity(id, name, owner.toEntity(), stargazersCount, updatedAt)
}