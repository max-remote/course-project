package com.maks.courseproject.di

import android.content.Context
import androidx.room.Room
import com.maks.courseproject.data.db.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(context: Context): AppDataBase =
        Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "app_database.bd"
        ).build()
}
