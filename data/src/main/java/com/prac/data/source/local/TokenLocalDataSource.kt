package com.prac.data.source.local

import com.prac.data.repository.model.TokenModel

internal interface TokenLocalDataSource {
    suspend fun setToken(token: TokenModel)

    suspend fun getToken(): TokenModel
}