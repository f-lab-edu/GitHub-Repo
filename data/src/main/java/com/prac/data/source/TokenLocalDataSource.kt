package com.prac.data.source

internal interface TokenLocalDataSource {
    fun setTokenLocal(
        accessToken: String,
        refreshToken: String
    )
}