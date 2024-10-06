package com.prac.data.source.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.prac.data.source.local.room.entity.Repository
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {
    @Query("SELECT * FROM repository")
    fun getRepositories(): PagingSource<Int, Repository>

    @Query("SELECT * FROM repository WHERE id = :id")
    fun getRepository(id: Int): Flow<Repository?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repos: List<Repository>)

    @Query("UPDATE repository SET isStarred = :isStarred, stargazersCount = :updatedCount WHERE id = :id")
    suspend fun updateStarStateAndStarCount(id: Int, isStarred: Boolean, updatedCount: Int)

    @Query("UPDATE repository SET isStarred = :isStarred WHERE id = :id")
    suspend fun updateStarState(id: Int, isStarred: Boolean)

    @Query("UPDATE repository SET stargazersCount = :updatedCount WHERE id = :id")
    suspend fun updateStarCount(id: Int, updatedCount: Int)

    @Query("DELETE FROM repository")
    suspend fun clearRepositories()
}