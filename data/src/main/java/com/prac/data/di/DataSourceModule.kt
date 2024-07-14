package com.prac.data.di

import com.prac.data.source.GetAccessTokenApiDataSource
import com.prac.data.source.api.GitHubTokenApi
import com.prac.data.source.impl.GetAccessTokenApiDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DataSourceModule {
    @Provides
    fun provideGetAccessTokenApiDataSource(
        gitHubTokenApi: GitHubTokenApi
    ): GetAccessTokenApiDataSource =
        GetAccessTokenApiDataSourceImpl(gitHubTokenApi)
}