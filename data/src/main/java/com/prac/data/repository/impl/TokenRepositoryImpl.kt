package com.prac.data.repository.impl

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
    ): Result<Unit> {
        try {
            val model = tokenApiDataSource.getToken(
                code = code
            )

            setToken(model.accessToken, model.refreshToken)

            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return tokenLocalDataSource.isLoggedIn()
    }

    private suspend fun setToken(accessToken: String, refreshToken: String) {
        tokenLocalDataSource.setToken(accessToken, refreshToken)
    }
}