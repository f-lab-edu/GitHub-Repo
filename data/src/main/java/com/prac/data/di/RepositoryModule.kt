package com.prac.data.di

import com.prac.data.repository.GetAccessTokenRepository
import com.prac.data.repository.impl.GetAccessTokenRepositoryImpl
import com.prac.data.source.GetAccessTokenDataSource
import com.prac.data.source.api.GitHubTokenApi
import com.prac.data.source.impl.GetAccessTokenDataSourceImpl
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
    fun provideGetAccessTokenRepository(
        accessTokenDataSource: GetAccessTokenDataSource
    ): GetAccessTokenRepository =
        GetAccessTokenRepositoryImpl(accessTokenDataSource)
}