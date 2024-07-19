package com.prac.data.source.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TokenModel(
    @SerialName("access_token") val accessToken: String,
    @SerialName("expires_in") val expiresIn: Int,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("refresh_token_expires_in") val refreshTokenExpiresIn: Int,
    @SerialName("scope") val scope: String,
    @SerialName("token_type") val tokenType: String
)