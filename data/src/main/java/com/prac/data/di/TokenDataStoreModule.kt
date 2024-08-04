package com.prac.data.di

import android.content.Context
import com.prac.data.di.datastore.TokenDataStoreManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object TokenDataStoreModule {
    @Provides
    @Singleton
    fun provideTokenDataStoreManager(@ApplicationContext context: Context) : TokenDataStoreManager {
        return TokenDataStoreManager(context)
    }
}