package com.prac.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.prac.data.di.annotation.BasicConverter
import com.prac.data.di.annotation.IgnoreUnknownConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter

@Module
@InstallIn(SingletonComponent::class)
object ConverterModule {
    @Provides
    @BasicConverter
    fun provideBasicConverter(): Converter.Factory {
        return Json.asConverterFactory("application/json".toMediaType())
    }
}