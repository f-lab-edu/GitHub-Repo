package com.prac.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prac.data.repository.model.RepoDetailModel
import com.prac.data.repository.model.RepoModel

internal abstract class RepoApiDataSource : PagingSource<Int, RepoModel>() {
    abstract override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoModel>

    abstract override fun getRefreshKey(state: PagingState<Int, RepoModel>): Int?

    abstract suspend fun getRepository(userName: String, repoName: String) : RepoDetailModel
}