package com.prac.data.repository.dto

import com.prac.data.di.entity.RepoEntity

internal data class RepoDto(
    val id: Int,
    val name: String,
    val owner: OwnerDto,
    val stargazersCount: Int,
    val updatedAt: String
) {
    fun toEntity() =
        RepoEntity(id, name, owner.toEntity(), stargazersCount, updatedAt)
}