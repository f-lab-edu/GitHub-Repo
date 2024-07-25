package com.prac.data.repository.impl

import com.prac.data.di.entity.RepoEntity
import com.prac.data.repository.RepoRepository
import com.prac.data.source.RepoApiDataSource
import javax.inject.Inject

internal class RepoRepositoryImpl @Inject constructor(
    private val repoApiDataSource: RepoApiDataSource
) : RepoRepository {
    override suspend fun getRepositories(userName: String): Result<List<RepoEntity>> {
        try {
            val dto = repoApiDataSource.getRepos(userName)

            return Result.success(dto.map { it.toEntity() })
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}