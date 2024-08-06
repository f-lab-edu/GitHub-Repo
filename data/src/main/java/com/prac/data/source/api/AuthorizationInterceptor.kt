package com.prac.data.source.api

import com.prac.data.di.datastore.TokenDataStoreManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val tokenDataStoreManager: TokenDataStoreManager
) : Interceptor {
    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val AUTHORIZATION_TYPE = "Bearer"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = "" // TODO run blocking to get access token
        val request = chain.request().newBuilder()
            .addHeader(AUTHORIZATION, "$AUTHORIZATION_TYPE $accessToken")
            .build()
        return chain.proceed(request)
    }

}