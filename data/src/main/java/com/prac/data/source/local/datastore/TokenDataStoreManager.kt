package com.prac.data.source.local.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.migrations.SharedPreferencesView
import com.google.protobuf.InvalidProtocolBufferException
import com.prac.data.datastore.Token
import com.prac.data.repository.model.TokenModel
import kotlinx.coroutines.flow.first
import java.io.InputStream
import java.io.OutputStream
import java.time.Instant
import java.time.ZoneId

internal class TokenDataStoreManager(
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
                        context.getSharedPreferences(
                            TOKEN_SHARED_PREFERENCES_NAME,
                            Context.MODE_PRIVATE
                        ).all.any {
                            it.value != null
                        }
                    }
                ) { sharedPreferences: SharedPreferencesView, token: Token ->
                    token.toBuilder()
                        .setAccessToken(sharedPreferences.getString(KEY.ACCESS_TOKEN.key, ""))
                        .setRefreshToken(sharedPreferences.getString(KEY.REFRESH_TOKEN.key, ""))
                        .setIsLoggedIn(sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false))
                        .build()
                },
                object : DataMigration<Token> {
                    override suspend fun cleanUp() {}

                    override suspend fun shouldMigrate(currentData: Token): Boolean {
                        return currentData.isLoggedIn
                    }

                    override suspend fun migrate(currentData: Token): Token {
                        return currentData.toBuilder()
                            .clearIsLoggedIn()
                            .build()
                    }
                }
            )
        }
    )

    suspend fun saveTokenData(token: TokenModel) {
        mContext.tokenDataStore.updateData { pref ->
            pref.toBuilder()
                .setAccessToken(token.accessToken)
                .setRefreshToken(token.refreshToken)
                .setAccessTokenExpiresInMinute(token.expiresInMinute)
                .setRefreshTokenExpiresInMinute(token.refreshTokenExpiresInMinute)
                .setAccessTokenUpdatedAt(token.updatedAt.toInstant().toEpochMilli())
                .build()
        }
    }

    suspend fun getToken(): TokenModel {
        return mContext.tokenDataStore.data.first().let {
            TokenModel(
                accessToken = it.accessToken,
                refreshToken = it.refreshToken,
                expiresInMinute = it.accessTokenExpiresInMinute,
                refreshTokenExpiresInMinute = it.refreshTokenExpiresInMinute,
                updatedAt = Instant.ofEpochMilli(it.accessTokenUpdatedAt).atZone(ZoneId.systemDefault())
            )
        }
    }

}