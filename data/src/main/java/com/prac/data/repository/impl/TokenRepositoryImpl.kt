package com.prac.data.repository.impl

import com.prac.data.entity.AccessTokenEntity
import com.prac.data.repository.TokenRepository
import com.prac.data.source.TokenApiDataSource
import com.prac.data.source.TokenLocalDataSource
import javax.inject.Inject

internal class TokenRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val tokenApiDataSource: TokenApiDataSource
) : TokenRepository {
    override suspend fun getTokenApi(
        code: String
    ): Result<AccessTokenEntity> {
        try {
            val model = tokenApiDataSource.getToken(
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

    private fun setToken(accessToken: String, refreshToken: String) {
        tokenLocalDataSource.setToken(accessToken, refreshToken)
    }
}