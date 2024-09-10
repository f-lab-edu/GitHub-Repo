package com.prac.data.di

import com.prac.data.BuildConfig
import com.prac.data.di.annotation.AuthOkHttpClient
import com.prac.data.di.annotation.BasicOkHttpClient
import com.prac.data.di.annotation.AuthRetrofit
import com.prac.data.di.annotation.BasicRetrofit
import com.prac.data.source.network.api.GitHubApi
import com.prac.data.source.network.api.GitHubTokenApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule {
    @Provides
    @Singleton
    @BasicRetrofit
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
    @AuthRetrofit
    fun provideGitHubRetrofit(
        @AuthOkHttpClient okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.GITHUB_API_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    fun provideGitHubTokenService(
        @BasicRetrofit retrofit: Retrofit
    ): GitHubTokenApi =
        retrofit.create(GitHubTokenApi::class.java)

    @Provides
    @Singleton
    fun provideGitHubService(
        @AuthRetrofit retrofit: Retrofit
    ): GitHubApi =
        retrofit.create(GitHubApi::class.java)
}