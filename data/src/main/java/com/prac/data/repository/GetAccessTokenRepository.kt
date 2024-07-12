package com.prac.data.repository

import com.prac.data.entity.AccessTokenEntity

interface GetAccessTokenRepository {
    suspend fun getAccessToken(
        clientID: String,
        clientSecret: String,
        code: String,
    ) : Result<AccessTokenEntity>
}