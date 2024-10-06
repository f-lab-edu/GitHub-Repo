package com.prac.data.source.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Owner(
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "avatarUrl") val avatarUrl: String
)