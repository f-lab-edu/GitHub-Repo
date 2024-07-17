package com.prac.data.di.binds

interface TokenSharedPreferences {
    enum class KEY(private val _key: String) {
        ACCESS_TOKEN("accessKey"),
        REFRESH_TOKEN("refreshKey");

        fun getKey() : String = _key
    }
    fun putToken(key: KEY, value: String)
    fun getToken(key: KEY) : String
}


