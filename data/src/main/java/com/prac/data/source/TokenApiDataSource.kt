package com.prac.data.source

import com.prac.data.repository.model.TokenModel

internal interface TokenApiDataSource {
    suspend fun getToken(
        code: String
    ) : TokenModel
}