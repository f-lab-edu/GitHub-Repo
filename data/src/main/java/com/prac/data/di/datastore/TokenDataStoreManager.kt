package com.prac.data.di.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.migrations.SharedPreferencesView
import com.google.protobuf.InvalidProtocolBufferException
import com.prac.data.datastore.Token
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream

class TokenDataStoreManager(
    private val mContext: Context
) {
    companion object {
        private const val TOKEN_SHARED_PREFERENCES_NAME = "token"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val TOKEN_DATA_STORE_NAME = "tokenDataStore"
    }

    enum class KEY(val key: String) {
        ACCESS_TOKEN("accessKey"),
        REFRESH_TOKEN("refreshKey")
    }

    private class TokenSerializer : Serializer<Token> {
        override val defaultValue: Token = Token.getDefaultInstance()
        override suspend fun readFrom(input: InputStream): Token {
            try {
                return Token.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }

        override suspend fun writeTo(t: Token, output: OutputStream) = t.writeTo(output)
    }

    private val Context.tokenDataStore: DataStore<Token> by dataStore(
        fileName = TOKEN_DATA_STORE_NAME,
        serializer = TokenSerializer(),
        produceMigrations = { context ->
            listOf(
                SharedPreferencesMigration(
                    context = context,
                    sharedPreferencesName = TOKEN_SHARED_PREFERENCES_NAME,
                    shouldRunMigration = {
                        context.getSharedPreferences(TOKEN_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).all.any {
                            it.value != null
                        }
                    }
                ) { sharedPreferences: SharedPreferencesView, token: Token ->
                    token.toBuilder()
                        .setAccessToken(sharedPreferences.getString(KEY.ACCESS_TOKEN.key, ""))
                        .setRefreshToken(sharedPreferences.getString(KEY.REFRESH_TOKEN.key, ""))
                        .setIsLoggedIn(sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false))
                        .build()
                }
            )
        }
    )

    suspend fun putToken(accessToken: String, refreshToken: String) {
        mContext.tokenDataStore.updateData { pref ->
            pref.toBuilder()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setIsLoggedIn(true)
                .build()
        }
    }

    suspend fun getToken(key: KEY) : String {
        return mContext.tokenDataStore.data
            .catch { emit(Token.getDefaultInstance()) }
            .map {
                if (key == KEY.ACCESS_TOKEN) it.accessToken
                else it.refreshToken
            }.first()
    }

    suspend fun isLoggedIn(): Boolean {
        return mContext.tokenDataStore.data
            .catch { emit(Token.getDefaultInstance()) }
            .first()
            .isLoggedIn
    }
}