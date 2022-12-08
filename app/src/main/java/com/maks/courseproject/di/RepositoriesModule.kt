package com.maks.courseproject.di

import com.maks.courseproject.data.repositories.RemoteRepositoryImpl
import dagger.Module

@Module
interface RepositoriesModule {
    fun bindRemoteRepository(repository: RemoteRepositoryImpl)
}
