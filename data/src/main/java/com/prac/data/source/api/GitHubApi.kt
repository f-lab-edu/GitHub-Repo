package com.prac.data.source.api

import com.prac.data.source.dto.RepoDetailDto
import com.prac.data.source.dto.RepoDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

internal interface GitHubApi {
    @GET("users/{userName}/repos")
    suspend fun getRepos(
        @Path("userName") userName: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): List<RepoDto>

    @GET("repos/{userName}/{repoName}")
    suspend fun getRepo(
        @Path("userName") userName: String,
        @Path("repoName") repoName: String
    ): RepoDetailDto

    @GET("user/starred/{userName}/{repoName}")
    suspend fun checkRepositoryIsStarred(
        @Path("userName") userName: String,
        @Path("repoName") repoName: String
    )

    @PUT("user/starred/{userName}/{repoName}")
    suspend fun starRepository(
        @Path("userName") userName: String,
        @Path("repoName") repoName: String
    )

    @DELETE("user/starred/{userName}/{repoName}")
    suspend fun unStarRepository(
        @Path("userName") userName: String,
        @Path("repoName") repoName: String
    )
}