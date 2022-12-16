package com.maks.courseproject.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.maks.courseproject.data.db.entity.CharacterLocate

object Converter {
        @JvmStatic
        @TypeConverter
        fun listToString(list: List<String>): String {
            return Gson().toJson(list)
        }

        @JvmStatic
        @TypeConverter
        fun stringToList(json: String): List<String> {
            val listType = object : TypeToken<List<String>>() {}.type
            return Gson().fromJson(json, listType)
        }

        @JvmStatic
        @TypeConverter
        fun locationToString(location: CharacterLocate): String {
            return Gson().toJson(location)
        }

        @JvmStatic
        @TypeConverter
        fun stringToLocation(json: String): CharacterLocate {
            val listType = object : TypeToken<CharacterLocate>() {}.type
            return Gson().fromJson(json, listType)
        }
    }