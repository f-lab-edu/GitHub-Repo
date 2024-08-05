package com.prac.data.repository.model

internal data class TokenModel(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Int,
    val refreshTokenExpiresIn: Int
)
