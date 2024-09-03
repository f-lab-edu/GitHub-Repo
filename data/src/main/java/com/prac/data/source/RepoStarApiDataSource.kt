package com.prac.data.source

internal interface RepoStarApiDataSource {
    suspend fun checkRepositoryIsStarred(repoName: String) : Boolean

    suspend fun starRepository(userName: String, repoName: String)

    suspend fun unStarRepository(userName: String, repoName: String)
}