package com.prac.data.repository

import com.prac.data.di.entity.RepoEntity

interface RepoRepository {
    suspend fun getRepositories(
        userName: String
    ) : Result<List<RepoEntity>>
}