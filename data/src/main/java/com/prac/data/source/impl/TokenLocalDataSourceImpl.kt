package com.prac.data.source.impl

import com.prac.data.di.datastore.TokenDataStoreManager
import com.prac.data.repository.model.TokenModel
import com.prac.data.source.TokenLocalDataSource
import javax.inject.Inject

internal class TokenLocalDataSourceImpl @Inject constructor(
    private val tokenDataStoreManager: TokenDataStoreManager
) : TokenLocalDataSource {
    override suspend fun setToken(token: TokenModel) {
        tokenDataStoreManager.saveTokenData(token)
    }

    override suspend fun getToken(): TokenModel {
        return tokenDataStoreManager.getToken()
    }
}