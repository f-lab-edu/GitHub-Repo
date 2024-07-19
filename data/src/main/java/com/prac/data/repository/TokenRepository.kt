package com.prac.data.repository

import com.prac.data.entity.AccessTokenEntity

interface TokenRepository {
    suspend fun getTokenApi(
        code: String
    ) : Result<AccessTokenEntity>
}