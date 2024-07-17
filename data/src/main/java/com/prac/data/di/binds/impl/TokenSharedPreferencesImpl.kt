package com.prac.data.di.binds.impl

import android.content.Context
import androidx.core.content.edit
import com.prac.data.di.binds.TokenSharedPreferences
import javax.inject.Inject

class TokenSharedPreferencesImpl @Inject constructor(
    private val context: Context
) : TokenSharedPreferences {

    companion object {
        private const val TOKEN_SHARED_PREFERENCES_NAME = "token"
    }

    private val tokenSharedPreferences = context.getSharedPreferences(TOKEN_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun putToken(key: TokenSharedPreferences.KEY, value: String) {
        tokenSharedPreferences.edit {
            putString(key.getKey(), value)
        }
    }

    override fun getToken(key: TokenSharedPreferences.KEY) : String{
        return tokenSharedPreferences.getString(key.getKey(), "") ?: ""
    }
}