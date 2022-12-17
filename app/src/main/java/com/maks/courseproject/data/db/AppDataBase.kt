package com.maks.courseproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maks.courseproject.data.db.dao.ApplicationDao
import com.maks.courseproject.data.db.entity.CharacterEntity
import com.maks.courseproject.data.db.entity.EpisodeEntity
import com.maks.courseproject.data.db.entity.LocationEntity

@Database(
    entities = [CharacterEntity::class, LocationEntity::class, EpisodeEntity::class],
    version = 1
)

@TypeConverters(Converter::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun applicationDao(): ApplicationDao
}