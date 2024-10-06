package com.prac.data.entity

data class RepoDetailEntity(
    val id: Int,
    val name: String,
    val owner: OwnerEntity,
    val stargazersCount: Int,
    val forksCount: Int,
    var isStarred: Boolean?
)