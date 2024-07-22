package com.prac.data.repository

import com.prac.data.di.entity.RepoEntity

interface RepoRepository {
    suspend fun getReposApi(
        userName: String
    ) : Result<List<RepoEntity>>
}