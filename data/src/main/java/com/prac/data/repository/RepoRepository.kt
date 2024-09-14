package com.prac.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.RemoteMediator
import com.prac.data.entity.RepoDetailEntity
import com.prac.data.entity.RepoEntity
import com.prac.data.source.local.room.entity.Repository
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
abstract class RepoRepository : RemoteMediator<Int, Repository>() {
    abstract suspend fun getRepositories() : Flow<PagingData<RepoEntity>>

    abstract suspend fun getRepository(userName: String, repoName: String) : Result<RepoDetailEntity>

    abstract suspend fun isStarred(id: Int, repoName: String)

    abstract suspend fun starRepository(userName: String, repoName: String) : Result<Unit>

    abstract suspend fun unStarRepository(userName: String, repoName: String) : Result<Unit>

    abstract suspend fun starLocalRepository(id: Int, updatedStarCount: Int)
}