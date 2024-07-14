package com.prac.data.di

import com.prac.data.repository.TokenRepository
import com.prac.data.repository.impl.TokenRepositoryImpl
import com.prac.data.source.GetAccessTokenApiDataSource
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
        accessTokenApiDataSource: GetAccessTokenApiDataSource
    ): TokenRepository =
        TokenRepositoryImpl(accessTokenApiDataSource)
}