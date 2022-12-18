package com.maks.courseproject.di

import com.maks.courseproject.data.repositories.RemoteRepository
import com.maks.courseproject.data.repositories.RemoteRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RepositoriesModule {

    @Binds
    fun bindRemoteRepository(repository: RemoteRepositoryImpl): RemoteRepository
}
