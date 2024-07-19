package com.prac.data.source.impl

import com.prac.data.repository.dto.AccessTokenDto
import com.prac.data.source.TokenApiDataSource
import com.prac.data.source.api.GitHubTokenApi
import javax.inject.Inject

internal class TokenApiDataSourceImpl @Inject constructor(
    private val gitHubTokenApi: GitHubTokenApi
) : TokenApiDataSource {
    override suspend fun getToken(
        code: String
    ): AccessTokenDto {
        val response = gitHubTokenApi.getAccessTokenApi(
            code = code
        )

        return AccessTokenDto(
            accessToken = response.accessToken,
            tokenType = response.tokenType
        )
    }
}