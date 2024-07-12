package com.prac.data.source.impl

import com.prac.data.repository.dto.AccessTokenDto
import com.prac.data.source.GetAccessTokenDataSource
import com.prac.data.source.api.GitHubTokenApi
import javax.inject.Inject

internal class GetAccessTokenDataSourceImpl @Inject constructor(
    private val gitHubTokenApi: GitHubTokenApi
) : GetAccessTokenDataSource {
    override suspend fun getAccessToken(
        clientID: String,
        clientSecret: String,
        code: String
    ): AccessTokenDto {
        val response = gitHubTokenApi.getAccessToken(
            clientID = clientID,
            clientSecret = clientSecret,
            code = code
        )

        return AccessTokenDto(
            accessToken = response.accessToken,
            tokenType = response.tokenType
        )
    }
}