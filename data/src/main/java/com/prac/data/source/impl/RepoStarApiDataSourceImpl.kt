package com.prac.data.source.impl

import com.prac.data.source.RepoStarApiDataSource
import com.prac.data.source.api.GitHubApi
import javax.inject.Inject

internal class RepoStarApiDataSourceImpl @Inject constructor(
    private val gitHubApi: GitHubApi
): RepoStarApiDataSource {
    override suspend fun checkRepositoryIsStarred(repoName: String) : Boolean {
        val response = gitHubApi.checkRepositoryIsStarred("GongDoMin", repoName)

        return response.code() == 204
    }
}