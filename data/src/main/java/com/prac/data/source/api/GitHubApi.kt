package com.prac.data.source.api

import com.prac.data.source.dto.RepoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface GitHubApi {
    @GET("users/{userName}/repos")
    suspend fun getRepos(
        @Path("userName") userName: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): List<RepoDto>

    @GET("user/starred/{userName}/{repo}")
    suspend fun checkStarred(
        @Path("userName") userName: String,
        @Path("repo") repo: String
    ): Response<Unit>
}