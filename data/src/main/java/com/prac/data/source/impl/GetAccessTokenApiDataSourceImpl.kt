package com.prac.data.source.impl

import com.prac.data.repository.dto.AccessTokenDto
import com.prac.data.source.GetAccessTokenApiDataSource
import com.prac.data.source.api.GitHubTokenApi
import javax.inject.Inject

internal class GetAccessTokenApiDataSourceImpl @Inject constructor(
    private val gitHubTokenApi: GitHubTokenApi
) : GetAccessTokenApiDataSource {
    override suspend fun getAccessTokenApi(
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