package com.prac.data.di.binds

interface TokenSharedPreferences {
    enum class KEY(val key: String) {
        ACCESS_TOKEN("accessKey"),
        REFRESH_TOKEN("refreshKey")
    }
    fun putToken(key: KEY, value: String)
    fun getToken(key: KEY) : String
    fun isLoggedIn() : Boolean
}


