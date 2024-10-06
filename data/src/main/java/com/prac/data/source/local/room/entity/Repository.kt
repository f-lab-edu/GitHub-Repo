package com.prac.data.source.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repository")
data class Repository(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @Embedded val owner: Owner,
    @ColumnInfo(name = "stargazersCount") val stargazersCount: Int,
    @ColumnInfo(name = "updatedAt") val updatedAt: String,
    @ColumnInfo(name = "isStarred") val isStarred: Boolean?
)
