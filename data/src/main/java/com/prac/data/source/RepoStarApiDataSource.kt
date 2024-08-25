package com.prac.data.source

internal interface RepoStarApiDataSource {
    suspend fun checkRepositoryIsStarred(repoName: String) : Boolean
}