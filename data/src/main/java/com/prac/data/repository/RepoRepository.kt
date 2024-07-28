package com.prac.data.repository

import androidx.paging.PagingData
import com.prac.data.entity.RepoEntity

interface RepoRepository {
    suspend fun getRepositories() : Result<PagingData<RepoEntity>>
}