package com.prac.data.repository

import androidx.paging.PagingData
import com.prac.data.entity.RepoEntity
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    suspend fun getRepositories() : Flow<PagingData<RepoEntity>>

    suspend fun isStarred(repoName: String) : Result<Boolean>
}