package com.prac.data.repository.model

import com.prac.data.entity.OwnerEntity

internal data class OwnerModel(
    val login: String,
    val avatarUrl: String
) {
    fun toEntity() =
        OwnerEntity(login, avatarUrl)
}