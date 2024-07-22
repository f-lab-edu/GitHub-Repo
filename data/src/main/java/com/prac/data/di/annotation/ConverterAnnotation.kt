package com.prac.data.di.annotation

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BasicConverter

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IgnoreUnknownConverter