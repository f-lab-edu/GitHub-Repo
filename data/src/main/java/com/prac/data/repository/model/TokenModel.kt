package com.prac.data.repository.model

import java.time.ZonedDateTime

internal data class TokenModel(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Int,
    val refreshTokenExpiresIn: Int,
    val updatedAt: ZonedDateTime
)
