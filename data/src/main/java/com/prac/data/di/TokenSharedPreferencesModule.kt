package com.prac.data.di

import com.prac.data.di.binds.TokenSharedPreferences
import com.prac.data.di.binds.impl.TokenSharedPreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class TokenSharedPreferencesModule {
    @Binds
    @Singleton
    abstract fun provideTokenSharedPreferences(tokenSharedPreferencesImpl: TokenSharedPreferencesImpl) : TokenSharedPreferences
}