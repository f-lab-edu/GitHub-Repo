package com.prac.data.source

internal interface TokenLocalDataSource {
    suspend fun setToken(
        accessToken: String,
        refreshToken: String
    )

    suspend fun isLoggedIn() : Boolean
}