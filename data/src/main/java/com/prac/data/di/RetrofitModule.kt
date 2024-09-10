package com.prac.data.di

import com.prac.data.BuildConfig
import com.prac.data.source.network.di.annotation.AuthOkHttpClient
import com.prac.data.source.network.di.annotation.BasicOkHttpClient
import com.prac.data.source.network.di.annotation.AuthRetrofit
import com.prac.data.source.network.di.annotation.BasicRetrofit
import com.prac.data.source.network.service.GitHubService
import com.prac.data.source.network.service.GitHubAuthService
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
    fun provideGitHubAuthService(
        @BasicRetrofit retrofit: Retrofit
    ): GitHubAuthService =
        retrofit.create(GitHubAuthService::class.java)

    @Provides
    @Singleton
    fun provideGitHubService(
        @AuthRetrofit retrofit: Retrofit
    ): GitHubService =
        retrofit.create(GitHubService::class.java)
}