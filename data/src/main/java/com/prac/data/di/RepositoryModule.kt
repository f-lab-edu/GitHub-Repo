package com.prac.data.di

import com.prac.data.repository.RepoRepository
import com.prac.data.repository.TokenRepository
import com.prac.data.repository.impl.RepoRepositoryImpl
import com.prac.data.repository.impl.TokenRepositoryImpl
import com.prac.data.source.RepoApiDataSource
import com.prac.data.source.TokenApiDataSource
import com.prac.data.source.TokenLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {
    @Provides
    @Singleton
    fun provideTokenRepository(
        tokenLocalDataSource: TokenLocalDataSource,
        tokenApiDataSource: TokenApiDataSource
    ): TokenRepository =
        TokenRepositoryImpl(tokenLocalDataSource, tokenApiDataSource)

    @Provides
    @Singleton
    fun provideRepoRepository(
        repoApiDataSource: RepoApiDataSource
    ): RepoRepository =
        RepoRepositoryImpl(repoApiDataSource)
}