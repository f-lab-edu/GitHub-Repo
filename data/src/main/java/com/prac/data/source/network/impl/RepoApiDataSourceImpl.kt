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
) : RepoApiDataSource() {
    companion object {
        const val STARTING_PAGE_INDEX = 1
        const val PAGE_SIZE = 10
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoModel> {
        try {
            val position = params.key ?: STARTING_PAGE_INDEX

            val response = gitHubService.getRepos("GongDoMin", params.loadSize, position)

            val nextKey = if (response.size < PAGE_SIZE) {
                null
            } else {
                position + (params.loadSize / PAGE_SIZE)
            }

            return LoadResult.Page(
                data = response.map {
                    RepoModel(it.id, it.name, OwnerModel(it.owner.login, it.owner.avatarUrl), it.stargazersCount, it.updatedAt)
                },
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RepoModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun getRepository(userName: String, repoName: String): RepoDetailModel {
        val response = gitHubService.getRepo(userName, repoName)

        return RepoDetailModel(response.id, response.name, OwnerModel(response.owner.login, response.owner.avatarUrl), response.stargazersCount, response.forksCount)
    }

}