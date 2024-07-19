package com.prac.data.source

import com.prac.data.repository.dto.AccessTokenDto

internal interface TokenApiDataSource {
    suspend fun getToken(
        code: String
    ) : AccessTokenDto
}