package com.prac.data.source.impl

import android.content.SharedPreferences
import androidx.core.content.edit
import com.prac.data.source.TokenLocalDataSource
import javax.inject.Inject

internal class TokenLocalDataSourceImpl @Inject constructor(
    private val tokenSharedPreferences: SharedPreferences
) : TokenLocalDataSource {
    override fun setTokenLocal(accessToken: String, refreshToken: String) {
        tokenSharedPreferences.edit {
            putString("accessToken", accessToken)
            putString("refreshToken", refreshToken)
        }
    }
}