package com.prac.data.source

import com.prac.data.repository.dto.AccessTokenDto
import kotlinx.coroutines.flow.Flow

internal interface GetAccessTokenDataSource {
    fun getAccessToken(
        clientID: String,
        clientSecret: String,
        code: String,
    ) : Flow<AccessTokenDto>
}