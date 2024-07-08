package com.prac.data.source.model

import kotlinx.serialization.Serializable

@Serializable
internal data class AccessTokenModel(
    val access_token: String,
    val scope: String,
    val token_type: String
)
