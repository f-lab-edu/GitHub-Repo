package com.prac.data.source.impl

import com.prac.data.di.datastore.TokenDataStoreManager
import com.prac.data.source.TokenLocalDataSource
import javax.inject.Inject

internal class TokenLocalDataSourceImpl @Inject constructor(
    private val tokenDataStoreManager: TokenDataStoreManager
) : TokenLocalDataSource {
    override suspend fun setToken(accessToken: String, refreshToken: String) {
        tokenDataStoreManager.apply {
            putToken(accessToken, refreshToken)
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return tokenDataStoreManager.isLoggedIn()
    }
}