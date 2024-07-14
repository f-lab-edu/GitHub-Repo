package com.prac.data.source

import com.prac.data.repository.dto.AccessTokenDto

internal interface GetAccessTokenApiDataSource {
    suspend fun getAccessTokenApi(
        clientID: String,
        clientSecret: String,
        code: String,
    ) : AccessTokenDto
}