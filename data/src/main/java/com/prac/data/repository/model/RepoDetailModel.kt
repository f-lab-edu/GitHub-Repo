package com.prac.data.repository.model

internal data class RepoDetailModel(
    val id: Int,
    val name: String,
    val owner: OwnerModel,
    val stargazersCount: Int,
    val forksCount: Int
)