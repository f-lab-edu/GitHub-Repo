package com.prac.data.di

import com.prac.data.di.datastore.TokenDataStoreManager
import com.prac.data.source.network.RepoApiDataSource
import com.prac.data.source.network.RepoStarApiDataSource
import com.prac.data.source.network.TokenApiDataSource
import com.prac.data.source.local.TokenLocalDataSource
import com.prac.data.source.network.service.GitHubService
import com.prac.data.source.network.service.GitHubAuthService
import com.prac.data.source.network.impl.RepoApiDataSourceImpl
import com.prac.data.source.network.impl.RepoStarApiDataSourceImpl
import com.prac.data.source.network.impl.TokenApiDataSourceImpl
import com.prac.data.source.local.impl.TokenLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DataSourceModule {
    @Provides
    fun provideTokenApiDataSource(
        gitHubAuthService: GitHubAuthService
    ): TokenApiDataSource =
        TokenApiDataSourceImpl(gitHubAuthService)

    @Provides
    fun provideTokenLocalDataSource(
        tokenDataStoreManager: TokenDataStoreManager
    ): TokenLocalDataSource =
        TokenLocalDataSourceImpl(tokenDataStoreManager)

    @Provides
    fun provideRepoApiDataSource(
        gitHubService: GitHubService
    ): RepoApiDataSource =
        RepoApiDataSourceImpl(gitHubService)

    @Provides
    fun provideRepoStarApiDataSource(
        gitHubService: GitHubService
    ): RepoStarApiDataSource =
        RepoStarApiDataSourceImpl(gitHubService)
}