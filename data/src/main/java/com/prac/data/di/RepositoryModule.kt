package com.prac.data.di

import com.prac.data.repository.TokenRepository
import com.prac.data.repository.impl.TokenRepositoryImpl
import com.prac.data.source.GetAccessTokenApiDataSource
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
    fun provideGetAccessTokenRepository(
        tokenLocalDataSource: TokenLocalDataSource,
        accessTokenApiDataSource: GetAccessTokenApiDataSource
    ): TokenRepository =
        TokenRepositoryImpl(tokenLocalDataSource, accessTokenApiDataSource)
}