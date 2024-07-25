package com.prac.data.source.api

import com.prac.data.source.dto.RepoDto
import retrofit2.http.GET
import retrofit2.http.Path

internal interface GitHubApi {
    @GET("users/{userName}/repos")
    suspend fun getRepos(
        @Path("userName") userName: String
    ): List<RepoDto>
}