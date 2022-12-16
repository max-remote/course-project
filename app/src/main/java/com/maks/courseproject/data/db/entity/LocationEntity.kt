package com.maks.courseproject.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations_table")
data class LocationEntity (
    @PrimaryKey val id: Int,
    val created: String,
    val dimension: String,
    val name: String,
    val type: String,
    @ColumnInfo(name = "residents")
    val residents: List<String>
)