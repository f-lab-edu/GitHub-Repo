package com.prac.data.entity

data class RepoEntity(
    val id: Int,
    val name: String,
    val owner: OwnerEntity,
    val stargazersCount: Int,
    val updatedAt: String,
    var isStarred: Boolean?
)