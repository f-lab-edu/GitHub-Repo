package com.prac.data.di

import com.prac.data.source.GetAccessTokenDataSource
import com.prac.data.source.api.GitHubTokenApi
import com.prac.data.source.impl.GetAccessTokenDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataSourceModule {
    @Provides
    fun provideGetAccessTokenDataSource(
        gitHubTokenApi: GitHubTokenApi
    ): GetAccessTokenDataSource =
        GetAccessTokenDataSourceImpl(gitHubTokenApi)
}