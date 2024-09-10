package com.prac.data.di

import com.prac.data.di.datastore.TokenDataStoreManager
import com.prac.data.source.network.RepoApiDataSource
import com.prac.data.source.network.RepoStarApiDataSource
import com.prac.data.source.network.TokenApiDataSource
import com.prac.data.source.TokenLocalDataSource
import com.prac.data.source.api.GitHubApi
import com.prac.data.source.api.GitHubTokenApi
import com.prac.data.source.network.impl.RepoApiDataSourceImpl
import com.prac.data.source.network.impl.RepoStarApiDataSourceImpl
import com.prac.data.source.network.impl.TokenApiDataSourceImpl
import com.prac.data.source.impl.TokenLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DataSourceModule {
    @Provides
    fun provideTokenApiDataSource(
        gitHubTokenApi: GitHubTokenApi
    ): TokenApiDataSource =
        TokenApiDataSourceImpl(gitHubTokenApi)

    @Provides
    fun provideTokenLocalDataSource(
        tokenDataStoreManager: TokenDataStoreManager
    ): TokenLocalDataSource =
        TokenLocalDataSourceImpl(tokenDataStoreManager)

    @Provides
    fun provideRepoApiDataSource(
        gitHubApi: GitHubApi
    ): RepoApiDataSource =
        RepoApiDataSourceImpl(gitHubApi)

    @Provides
    fun provideRepoStarApiDataSource(
        gitHubApi: GitHubApi
    ): RepoStarApiDataSource =
        RepoStarApiDataSourceImpl(gitHubApi)
}