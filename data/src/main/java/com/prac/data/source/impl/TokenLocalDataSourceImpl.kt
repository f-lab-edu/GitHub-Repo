package com.prac.data.source.impl

import com.prac.data.di.datastore.TokenDataStoreManager
import com.prac.data.source.TokenLocalDataSource
import javax.inject.Inject

internal class TokenLocalDataSourceImpl @Inject constructor(
    private val tokenDataStoreManager: TokenDataStoreManager
) : TokenLocalDataSource {
    override suspend fun setToken(accessToken: String, refreshToken: String) {
        // TODO save token to local data store
    }

    override suspend fun isLoggedIn(): Boolean {
        TODO("Not yet implemented")
    }
}