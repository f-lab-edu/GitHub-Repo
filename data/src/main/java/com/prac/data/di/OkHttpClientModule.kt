package com.prac.data.di

import com.prac.data.di.binds.TokenSharedPreferences
import com.prac.data.source.api.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor

@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientModule {
    @Provides
    fun provideAuthorizationInterceptor(tokenSharedPreferences: TokenSharedPreferences) : Interceptor =
        AuthorizationInterceptor(tokenSharedPreferences)


}