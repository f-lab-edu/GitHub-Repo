package com.prac.data.di.entity

data class RepoEntity(
    val id: Int,
    val name: String,
    val owner: OwnerEntity,
    val stargazersCount: Int,
    val updatedAt: String
)