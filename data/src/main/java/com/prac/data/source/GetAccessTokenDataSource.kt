package com.prac.data.source

import com.prac.data.repository.dto.AccessTokenDto

internal interface GetAccessTokenDataSource {
    suspend fun getAccessToken(
        clientID: String,
        clientSecret: String,
        code: String,
    ) : AccessTokenDto
}