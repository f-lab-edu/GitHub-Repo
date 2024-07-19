package com.prac.data.source

import com.prac.data.repository.dto.AccessTokenDto

internal interface GetAccessTokenApiDataSource {
    suspend fun getAccessTokenApi(
        code: String
    ) : AccessTokenDto
}