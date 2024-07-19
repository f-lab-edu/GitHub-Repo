package com.prac.data.di.binds.impl

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.prac.data.di.binds.TokenSharedPreferences
import javax.inject.Inject

class TokenSharedPreferencesImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : TokenSharedPreferences {
    override fun putToken(key: TokenSharedPreferences.KEY, value: String) {
        sharedPreferences.edit {
            putString(key.key, value)
        }
    }

    override fun getToken(key: TokenSharedPreferences.KEY) : String{
        return sharedPreferences.getString(key.key, "") ?: ""
    }
}