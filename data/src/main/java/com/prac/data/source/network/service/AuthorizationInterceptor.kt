package com.prac.data.source.network.service

import com.prac.data.di.datastore.TokenDataStoreManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class AuthorizationInterceptor @Inject constructor(
    private val tokenDataStoreManager: TokenDataStoreManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking { tokenDataStoreManager.getToken() }
        if (accessToken.isExpired) {
            // TODO refresh access token by refresh token
            // 하지만 refresh 로직은 따로 구현하는 것으로 하고 여기서는 401을 리턴한다.
            return UNAUTHORIZED
        }
        val request = chain.request().newBuilder()
            .addHeader(AUTHORIZATION, "$AUTHORIZATION_TYPE $accessToken")
            .build()
        return chain.proceed(request)
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val AUTHORIZATION_TYPE = "Bearer"

        private val UNAUTHORIZED = Response.Builder()
            .code(401)
            .message("Unauthorized")
            .build()
    }
}