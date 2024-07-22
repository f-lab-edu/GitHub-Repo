package com.prac.data.repository.dto

import com.prac.data.di.entity.OwnerEntity

internal data class OwnerDto(
    val login: String,
    val avatarUrl: String
) {
    fun toEntity() =
        OwnerEntity(login, avatarUrl)
}