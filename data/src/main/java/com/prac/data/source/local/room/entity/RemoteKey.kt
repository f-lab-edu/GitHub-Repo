package com.prac.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class RemoteKey(
    @PrimaryKey val repoId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)