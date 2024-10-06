package com.prac.data.source.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter

@Module
@InstallIn(SingletonComponent::class)
class ConverterModule {
    @Provides
    fun provideIgnoreUnknownConverter(): Converter.Factory {
        val json = Json { ignoreUnknownKeys = true }
        return json.asConverterFactory("application/json".toMediaType())
    }
}