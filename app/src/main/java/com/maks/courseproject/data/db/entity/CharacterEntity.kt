package com.maks.courseproject.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "characters_table")
data class CharacterEntity (
    @PrimaryKey val id: Int,
    val created: String,
    val gender: String,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    @ColumnInfo(name = "location")
    val location: CharacterLocate,
    @ColumnInfo(name = "origin")
    val origin: CharacterLocate,
    @ColumnInfo(name = "episodes")
    val episode: List<String>,
    )