package com.prac.data.repository

import com.prac.data.entity.AccessTokenEntity
import com.prac.data.repository.dto.AccessTokenDto
import kotlinx.coroutines.flow.Flow

interface GetAccessTokenRepository {
    fun getAccessToken(
        clientID: String,
        clientSecret: String,
        code: String,
    ) : Flow<AccessTokenEntity>
}