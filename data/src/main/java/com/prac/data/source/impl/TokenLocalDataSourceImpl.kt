package com.prac.data.source.impl

import com.prac.data.di.binds.TokenSharedPreferences
import com.prac.data.source.TokenLocalDataSource
import javax.inject.Inject

internal class TokenLocalDataSourceImpl @Inject constructor(
    private val tokenSharedPreferences: TokenSharedPreferences
) : TokenLocalDataSource {
    override suspend fun setToken(accessToken: String, refreshToken: String) {
        tokenSharedPreferences.apply {
            putToken(TokenSharedPreferences.KEY.ACCESS_TOKEN, accessToken)
            putToken(TokenSharedPreferences.KEY.REFRESH_TOKEN, refreshToken)
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return tokenSharedPreferences.isLoggedIn()
    }
}