package com.prac.data.source.impl

import com.prac.data.repository.dto.RepoDto
import com.prac.data.source.RepoApiDataSource
import com.prac.data.source.api.GitHubApi
import javax.inject.Inject

internal class RepoApiDataSourceImpl @Inject constructor(
    private val gitHubApi: GitHubApi
) : RepoApiDataSource {
    override suspend fun getRepos(userName: String): List<RepoDto> {
        val response = gitHubApi.getRepos(userName)

        return response.map { it.toModel() }
    }
}