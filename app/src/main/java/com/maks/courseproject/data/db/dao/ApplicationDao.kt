package com.maks.courseproject.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maks.courseproject.data.db.entity.CharacterEntity
import com.maks.courseproject.data.db.entity.EpisodeEntity
import com.maks.courseproject.data.db.entity.LocationEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ApplicationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<CharacterEntity?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity)

    @Query("SELECT * FROM characters_table")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characters_table WHERE id = :id")
    suspend fun getCharacterId(id: Int): CharacterEntity

    @Query("DELETE FROM characters_table")
    suspend fun deleteAllCharacters()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEpisodes(episodes: List<EpisodeEntity?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episode: EpisodeEntity)

    @Query("SELECT * FROM episodes_table")
    fun getAllEpisodes(): Flow<List<EpisodeEntity>>

    @Query("SELECT * FROM episodes_table WHERE id = :id")
    suspend fun getEpisodeId(id: Int): EpisodeEntity

    @Query("DELETE FROM episodes_table")
    suspend fun deleteAllEpisodes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllLocations(locations: List<LocationEntity?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity)

    @Query("SELECT * FROM locations_table")
    fun getAllLocations(): Flow<List<LocationEntity>>

    @Query("DELETE FROM locations_table")
    suspend fun deleteAllLocations()

    @Query("SELECT * FROM locations_table WHERE id = :id")
    suspend fun getLocationId(id: Int): LocationEntity
}