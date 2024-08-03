package com.prac.data.repository

import com.prac.data.entity.RepoEntity
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    suspend fun getRepositories() : Flow<PagingData<RepoEntity>>
}