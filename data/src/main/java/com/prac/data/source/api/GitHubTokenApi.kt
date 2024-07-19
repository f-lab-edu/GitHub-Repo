package com.prac.data.source.api

import com.prac.data.BuildConfig
import com.prac.data.source.model.TokenModel
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

internal interface GitHubTokenApi {
    @POST("login/oauth/access_token")
    suspend fun getAccessTokenApi(
        @Header("Accept") accept: String = "application/json",
        @Query("client_id") clientID: String = BuildConfig.CLIENT_ID,
        @Query("client_secret") clientSecret: String = BuildConfig.CLIENT_SECRET,
        @Query("code") code: String,
    ): TokenModel
}