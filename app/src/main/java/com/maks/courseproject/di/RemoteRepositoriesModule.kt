package com.maks.courseproject.di

import com.maks.courseproject.data.repositories.DataBaseRepository
import com.maks.courseproject.data.repositories.DataBaseRepositoryImpl
import com.maks.courseproject.data.repositories.RemoteRepository
import com.maks.courseproject.data.repositories.RemoteRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RemoteRepositoriesModule {

    @Binds
    fun bindRemoteRepository(repository: RemoteRepositoryImpl): RemoteRepository

    @Binds
    fun bindDataBaseRepository(repository: DataBaseRepositoryImpl): DataBaseRepository
}
