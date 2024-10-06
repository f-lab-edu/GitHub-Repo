package com.prac.data.source.local.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.prac.data.source.local.room.entity.Repository

@Dao
interface RepositoryDao {
    @Query("SELECT * FROM repository")
    fun getRepositories(): PagingSource<Int, Repository>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repos: List<Repository>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRepository(repo: Repository)

    @Query("DELETE FROM repository")
    suspend fun clearRepositories()
}