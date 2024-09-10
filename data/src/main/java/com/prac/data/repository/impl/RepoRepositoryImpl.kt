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
import com.prac.data.source.local.room.entity.RemoteKey
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

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getRepositories(): Flow<PagingData<RepoEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = this,
            pagingSourceFactory = { repositoryDatabase.repositoryDao().getRepositories() }
        ).flow
            .map { pagingData ->
                pagingData.map { repository ->
                    RepoEntity(repository.id, repository.name, OwnerEntity(repository.owner.login, repository.owner.avatarUrl), repository.stargazersCount, repository.updatedAt, repository.isStarred)
                }
            }
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
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        TODO("Not yet implemented")
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Repository>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                repositoryDatabase.remoteKeyDao().remoteKey(repoId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Repository>): RemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                repositoryDatabase.remoteKeyDao().remoteKey(repo.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Repository>): RemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                repositoryDatabase.remoteKeyDao().remoteKey(repo.id)
            }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val PAGE_SIZE = 10
    }
}

