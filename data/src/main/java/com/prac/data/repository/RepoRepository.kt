package com.prac.data.repository

import com.prac.data.entity.RepoEntity

interface RepoRepository {
    suspend fun getRepositories(
        userName: String
    ) : Result<List<RepoEntity>>
}