package com.prac.data.source.local.di

import android.content.Context
import com.prac.data.source.local.room.database.RepositoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class DatabaseModule {
    @Provides
    fun provideRepositoryDatabase(@ApplicationContext context: Context) : RepositoryDatabase {
        return RepositoryDatabase.getInstance(context)
    }
}