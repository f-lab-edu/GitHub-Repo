package com.prac.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prac.data.source.local.room.entity.RemoteKey

@Dao
interface RemoteKeyDao {
    @Query("SELECT * FROM remote_key WHERE repoId = :repoId")
    suspend fun remoteKey(repoId: Int): RemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKeys: List<RemoteKey>)

    @Query("DELETE FROM remote_key")
    suspend fun clearRemoteKeys()
}