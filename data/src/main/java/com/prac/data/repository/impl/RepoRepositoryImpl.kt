package com.prac.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.prac.data.entity.OwnerEntity
import com.prac.data.entity.RepoEntity
import com.prac.data.repository.RepoRepository
import com.prac.data.source.RepoApiDataSource
import com.prac.data.source.RepoStarApiDataSource
import com.prac.data.source.impl.RepoApiDataSourceImpl.Companion.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class RepoRepositoryImpl @Inject constructor(
    private val repoApiDataSource: RepoApiDataSource,
    private val repoStarApiDataSource: RepoStarApiDataSource
) : RepoRepository {
    override suspend fun getRepositories(): Flow<PagingData<RepoEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { repoApiDataSource }
        ).flow
            .map { pagingData ->
                pagingData.map { repoModel ->
                    RepoEntity(repoModel.id, repoModel.name, OwnerEntity(repoModel.owner.login, repoModel.owner.avatarUrl), repoModel.stargazersCount, repoModel.updatedAt, null)
                }
            }

    override suspend fun isStarred(repoName: String): Result<Boolean> {
        return try {
            val result = repoStarApiDataSource.checkRepositoryIsStarred(repoName)

            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun starRepository(userName: String, repoName: String): Result<Unit> {
        return try {
            repoStarApiDataSource.starRepository(userName, repoName)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun unStarRepository(userName: String, repoName: String): Result<Unit> {
        return try {
            repoStarApiDataSource.unStarRepository(userName, repoName)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

