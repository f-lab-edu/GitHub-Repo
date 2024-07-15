package com.prac.data.repository.impl

import com.prac.data.entity.AccessTokenEntity
import com.prac.data.repository.TokenRepository
import com.prac.data.source.GetAccessTokenApiDataSource
import com.prac.data.source.TokenLocalDataSource
import javax.inject.Inject

internal class TokenRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val getAccessTokenApiDataSource: GetAccessTokenApiDataSource
) : TokenRepository {
    override suspend fun getAccessTokenApi(
        clientID: String,
        clientSecret: String,
        code: String
    ): Result<AccessTokenEntity> {
        try {
            val model = getAccessTokenApiDataSource.getAccessTokenApi(
                clientID = clientID,
                clientSecret = clientSecret,
                code = code
            )

            return Result.success(
                AccessTokenEntity(
                    accessToken = model.accessToken,
                    tokenType = model.tokenType
                )
            )
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private fun setTokenLocal(accessToken: String, refreshToken: String) {
        tokenLocalDataSource.setTokenLocal(accessToken, refreshToken)
    }
}