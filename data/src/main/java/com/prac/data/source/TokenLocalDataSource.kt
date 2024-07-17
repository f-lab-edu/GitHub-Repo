package com.prac.data.source

internal interface TokenLocalDataSource {
    fun setToken(
        accessToken: String,
        refreshToken: String
    )
}