package com.prac.data.repository.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingState
import androidx.paging.map
import com.prac.data.entity.OwnerEntity
import com.prac.data.entity.RepoDetailEntity
import com.prac.data.entity.RepoEntity
import com.prac.data.repository.RepoRepository
import com.prac.data.source.local.room.database.RepositoryDatabase
import com.prac.data.source.local.room.entity.Repository
import com.prac.data.source.network.RepoApiDataSource
import com.prac.data.source.network.RepoStarApiDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class RepoRepositoryImpl @Inject constructor(
    private val repoApiDataSource: RepoApiDataSource,
    private val repoStarApiDataSource: RepoStarApiDataSource,
    private val repositoryDatabase: RepositoryDatabase
) : RepoRepository() {
    override suspend fun getRepositories(): Flow<PagingData<RepoEntity>> {
        TODO()
    }

    override suspend fun getRepository(userName: String, repoName: String): Result<RepoDetailEntity> {
        return try {
            val model = repoApiDataSource.getRepository(userName, repoName)

            Result.success(
                RepoDetailEntity(
                    model.id, model.name, OwnerEntity(model.owner.login, model.owner.avatarUrl), model.stargazersCount, model.forksCount, null
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
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

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Repository>): MediatorResult {
        TODO("Not yet implemented")
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val PAGE_SIZE = 10
    }
}

