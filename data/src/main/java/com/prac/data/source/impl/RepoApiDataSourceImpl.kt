package com.prac.data.source.impl

import androidx.paging.PagingState
import com.prac.data.repository.model.RepoModel
import com.prac.data.source.RepoApiDataSource
import com.prac.data.source.api.GitHubApi
import javax.inject.Inject

internal class RepoApiDataSourceImpl @Inject constructor(
    private val gitHubApi: GitHubApi
) : RepoApiDataSource() {
    companion object {
        const val STARTING_PAGE_INDEX = 1
        const val PAGE_SIZE = 10
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoModel> {
        val position = params.key ?: STARTING_PAGE_INDEX

        val response = gitHubApi.getRepos("GongDoMin", params.loadSize, position)

        val nextKey = if (response.size < PAGE_SIZE) {
            null
        } else {
            position + (params.loadSize / PAGE_SIZE)
        }

        return LoadResult.Page(
            data = response.map { it.toModel() },
            prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
            nextKey = nextKey
        )
    }

    override fun getRefreshKey(state: PagingState<Int, RepoModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}