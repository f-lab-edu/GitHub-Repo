package com.prac.data.source.network.impl

import androidx.paging.PagingState
import com.prac.data.repository.model.OwnerModel
import com.prac.data.repository.model.RepoDetailModel
import com.prac.data.repository.model.RepoModel
import com.prac.data.source.network.RepoApiDataSource
import com.prac.data.source.network.service.GitHubService
import javax.inject.Inject

internal class RepoApiDataSourceImpl @Inject constructor(
    private val gitHubService: GitHubService
) : RepoApiDataSource {
    override suspend fun getRepositories(userName: String, perPage: Int, page: Int): List<RepoModel> {
        val response = gitHubService.getRepos(userName, perPage, page)

        return response.map {
            RepoModel(it.id, it.name, OwnerModel(it.owner.login, it.owner.avatarUrl), it.stargazersCount, it.updatedAt)
        }
    }

    override suspend fun getRepository(userName: String, repoName: String): RepoDetailModel {
        val response = gitHubService.getRepo(userName, repoName)

        return RepoDetailModel(response.id, response.name, OwnerModel(response.owner.login, response.owner.avatarUrl), response.stargazersCount, response.forksCount)
    }

}