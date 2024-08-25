package com.prac.data.repository.model

internal data class RepoModel(
    val id: Int,
    val name: String,
    val owner: OwnerModel,
    val stargazersCount: Int,
    val updatedAt: String
)