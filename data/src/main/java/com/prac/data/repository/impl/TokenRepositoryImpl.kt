package com.prac.data.repository.impl

import com.prac.data.repository.TokenRepository
import com.prac.data.repository.model.TokenModel
import com.prac.data.source.TokenApiDataSource
import com.prac.data.source.TokenLocalDataSource
import javax.inject.Inject

internal class TokenRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val tokenApiDataSource: TokenApiDataSource
) : TokenRepository {
    override suspend fun getTokenApi(code: String): Result<Unit> {
        try {
            val model = tokenApiDataSource.getToken(code)
            setToken(model)

            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return tokenLocalDataSource.isLoggedIn()
    }

    private suspend fun setToken(token: TokenModel) {
        tokenLocalDataSource.setToken(token)
    }
}