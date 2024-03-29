package com.maks.courseproject.di

import com.maks.courseproject.data.network.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideApiService(): ApiService = ApiService.create()
}
