package com.prac.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.prac.data.BuildConfig
import com.prac.data.di.annotation.BasicOkHttpClient
import com.prac.data.source.api.GitHubTokenApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule {
    @Provides
    @Singleton
    fun provideGitHubTokenRetrofit(
        @BasicOkHttpClient okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.GITHUB_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    fun provideGitHubTokenService(
        retrofit: Retrofit
    ): GitHubTokenApi =
        retrofit.create(GitHubTokenApi::class.java)
}