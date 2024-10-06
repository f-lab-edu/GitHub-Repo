package com.prac.data.repository

import androidx.paging.PagingData
import com.prac.data.entity.RepoDetailEntity
import com.prac.data.entity.RepoEntity
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    suspend fun getRepositories() : Flow<PagingData<RepoEntity>>

    suspend fun getRepository(userName: String, repoName: String) : Result<RepoDetailEntity>

    suspend fun isStarred(repoName: String) : Result<Boolean>

    suspend fun starRepository(userName: String, repoName: String) : Result<Unit>

    suspend fun unStarRepository(userName: String, repoName: String) : Result<Unit>
}