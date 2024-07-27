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
}