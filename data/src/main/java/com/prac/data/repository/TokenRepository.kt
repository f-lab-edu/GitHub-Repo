package com.prac.data.repository

import com.prac.data.entity.AccessTokenEntity

interface TokenRepository {
    suspend fun getAccessTokenApi(
        clientID: String,
        clientSecret: String,
        code: String,
    ) : Result<AccessTokenEntity>
}