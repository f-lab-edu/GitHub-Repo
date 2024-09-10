package com.prac.data.di

import com.prac.data.BuildConfig
import com.prac.data.di.annotation.AuthOkHttpClient
import com.prac.data.di.annotation.BasicOkHttpClient
import com.prac.data.source.local.datastore.TokenDataStoreManager
import com.prac.data.source.network.service.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object OkHttpClientModule {
    @Provides
    fun provideAuthorizationInterceptor(tokenDataStoreManager: TokenDataStoreManager) : Interceptor =
        AuthorizationInterceptor(tokenDataStoreManager)

    @Provides
    @Singleton
    @BasicOkHttpClient
    fun provideBasicOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(timeout = 10, unit = TimeUnit.SECONDS)
            .readTimeout(timeout = 10, unit = TimeUnit.SECONDS)
            .writeTimeout(timeout = 10, unit = TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
                }
            )
            .build()

    @Provides
    @Singleton
    @AuthOkHttpClient
    fun provideAuthorizationOkHttpClient(authorizationInterceptor: AuthorizationInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(timeout = 10, unit = TimeUnit.SECONDS)
            .readTimeout(timeout = 10, unit = TimeUnit.SECONDS)
            .writeTimeout(timeout = 10, unit = TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
                }
            )
            .addInterceptor(authorizationInterceptor)
            .build()
}