package com.prac.data.source.impl

import com.prac.data.source.RepoStarApiDataSource
import com.prac.data.source.api.GitHubApi
import javax.inject.Inject

internal class RepoStarApiDataSourceImpl @Inject constructor(
    private val gitHubApi: GitHubApi
): RepoStarApiDataSource {
    override suspend fun checkRepositoryIsStarred(repoName: String) : Boolean {
        return try {
            gitHubApi.checkRepositoryIsStarred("GongDoMin", repoName)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun starRepository(userName: String, repoName: String) {
        gitHubApi.starRepository(userName, repoName)
    }
}