package com.prac.data.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SharedPreferencesModule {

    private const val TOKEN_SHARED_PREFERENCES_NAME = "token"

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context) : SharedPreferences =
        context.getSharedPreferences(TOKEN_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
}