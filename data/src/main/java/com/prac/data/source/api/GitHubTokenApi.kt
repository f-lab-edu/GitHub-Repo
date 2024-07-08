package com.prac.data.source.api

import com.prac.data.source.model.AccessTokenModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

internal interface GitHubTokenApi {
    @POST("login/oauth/access_token")
    fun getAccessToken(
        @Header("Accept") accept: String = "application/json",
        @Query("client_id") clientID: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String,
    ): Response<AccessTokenModel>
}