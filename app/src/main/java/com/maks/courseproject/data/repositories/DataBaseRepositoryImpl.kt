package com.maks.courseproject.data.repositories

import com.maks.courseproject.data.db.AppDataBase
import com.maks.courseproject.data.db.entity.CharacterEntity
import com.maks.courseproject.data.db.entity.EpisodeEntity
import com.maks.courseproject.data.db.entity.LocationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DataBaseRepository{
    suspend fun addCharacter(characterEntity: CharacterEntity)

    suspend fun getCharacter(characterId: Int): CharacterEntity?

    suspend fun addEpisode(episodeEntity: EpisodeEntity)

    suspend fun getEpisode(episodeId: Int): EpisodeEntity?

    suspend fun addLocation(locationEntity: LocationEntity)

    suspend fun getLocation(locationId: Int): LocationEntity?

    suspend fun getAlLCharacters() : Flow<List<CharacterEntity>>
}

class DataBaseRepositoryImpl @Inject constructor(
private val appDB: AppDataBase
) : DataBaseRepository {
    override suspend fun addCharacter(characterEntity: CharacterEntity) {
        appDB.applicationDao().insertCharacter(characterEntity)
    }

    override suspend fun getCharacter(characterId: Int): CharacterEntity {
      return  appDB.applicationDao().getCharacterId(characterId)
    }

    override suspend fun addEpisode(episodeEntity: EpisodeEntity) {
        appDB.applicationDao().insertEpisode(episodeEntity)
    }

    override suspend fun getEpisode(episodeId: Int): EpisodeEntity {
        return  appDB.applicationDao().getEpisodeId(episodeId)
    }

    override suspend fun addLocation(locationEntity: LocationEntity) {
        appDB.applicationDao().getAllLocations()
    }

    override suspend fun getLocation(locationId: Int): LocationEntity {
        return  appDB.applicationDao().getLocationId(locationId)
    }

    override suspend fun getAlLCharacters(): Flow<List<CharacterEntity>> {
        return appDB.applicationDao().getAllCharacters()
    }
}
