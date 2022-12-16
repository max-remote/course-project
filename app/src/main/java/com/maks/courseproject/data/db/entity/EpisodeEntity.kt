package com.maks.courseproject.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes_table")
data class EpisodeEntity(
    @PrimaryKey val id: Int,
    val air_date: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val name: String,
)
