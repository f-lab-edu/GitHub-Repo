package com.prac.data.source

import com.prac.data.repository.dto.TokenDto

internal interface TokenApiDataSource {
    suspend fun getToken(
        code: String
    ) : TokenDto
}