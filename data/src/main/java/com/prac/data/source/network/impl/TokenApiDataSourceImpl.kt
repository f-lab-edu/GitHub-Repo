package com.prac.data.source.network.impl

import com.prac.data.repository.model.TokenModel
import com.prac.data.source.network.TokenApiDataSource
import com.prac.data.source.api.GitHubTokenApi
import java.time.ZonedDateTime
import javax.inject.Inject

internal class TokenApiDataSourceImpl @Inject constructor(
    private val gitHubTokenApi: GitHubTokenApi
) : TokenApiDataSource {
    override suspend fun getToken(
        code: String
    ): TokenModel {
        val response = gitHubTokenApi.getAccessTokenApi(
            code = code
        )

        return TokenModel(
            accessToken = response.accessToken,
            refreshToken = response.refreshToken,
            expiresInMinute = response.expiresIn,
            refreshTokenExpiresInMinute = response.refreshTokenExpiresIn,
            updatedAt = ZonedDateTime.now()
        )
    }
}