package com.prac.data.source.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TokenDto(
    @SerialName("access_token") val accessToken: String = "",
    @SerialName("expires_in") val expiresIn: Int = 0,
    @SerialName("refresh_token") val refreshToken: String = "",
    @SerialName("refresh_token_expires_in") val refreshTokenExpiresIn: Int = 0,
    @SerialName("scope") val scope: String = "",
    @SerialName("token_type") val tokenType: String = ""
)