package com.prac.data.repository.model

import java.time.ZonedDateTime

internal data class TokenModel(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Int,
    val refreshTokenExpiresIn: Int,
    val updatedAt: ZonedDateTime
) {
    val isExpired: Boolean
        get() = updatedAt.plusSeconds(expiresIn.toLong()).isBefore(ZonedDateTime.now())

    val isRefreshTokenExpired: Boolean
        get() = updatedAt.plusSeconds(refreshTokenExpiresIn.toLong()).isBefore(ZonedDateTime.now())
}
