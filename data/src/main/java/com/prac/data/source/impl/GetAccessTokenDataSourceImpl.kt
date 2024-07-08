package com.prac.data.source.impl

import com.prac.data.repository.dto.AccessTokenDto
import com.prac.data.source.GetAccessTokenDataSource
import com.prac.data.source.api.GitHubTokenApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

internal class GetAccessTokenDataSourceImpl @Inject constructor(
    private val gitHubTokenApi: GitHubTokenApi
) : GetAccessTokenDataSource {
    override fun getAccessToken(
        clientID: String,
        clientSecret: String,
        code: String
    ): Flow<AccessTokenDto> {
        return try {
            flow {
                val response = gitHubTokenApi.getAccessToken(
                    clientID = clientID,
                    clientSecret = clientSecret,
                    code = code
                )

                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(
                            AccessTokenDto(
                                accessToken = it.access_token,
                                tokenType = it.token_type
                            )
                        )
                    } ?: throw IOException()
                } else {
                    throw IOException()
                }
            }
        } catch (e: Exception) {
            throw e
        }

    }
}