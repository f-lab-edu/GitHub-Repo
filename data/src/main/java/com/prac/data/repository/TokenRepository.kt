package com.prac.data.repository

interface TokenRepository {
    suspend fun getTokenApi(
        code: String
    ) : Result<Unit>

    suspend fun isLoggedIn() : Boolean
}