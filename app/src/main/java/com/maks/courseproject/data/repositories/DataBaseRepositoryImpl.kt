package com.maks.courseproject.data.repositories

import com.maks.courseproject.data.db.dao.ApplicationDao
import com.maks.courseproject.data.db.entity.CharacterEntity
import com.maks.courseproject.data.db.entity.EpisodeEntity
import com.maks.courseproject.data.db.entity.LocationEntity
import javax.inject.Inject

interface DataBaseRepository{
    suspend fun addCharacter(characterEntity: CharacterEntity)

    suspend fun getCharacter(characterId: Int): CharacterEntity?

    suspend fun addEpisode(episodeEntity: EpisodeEntity)

    suspend fun getEpisode(episodeId: Int): EpisodeEntity?

    suspend fun addLocation(locationEntity: LocationEntity)

    suspend fun getLocation(locationId: Int): LocationEntity?
}

class DataBaseRepositoryImpl @Inject constructor(
private val applicationDao: ApplicationDao
) : DataBaseRepository {
    override suspend fun addCharacter(characterEntity: CharacterEntity) {
        applicationDao.insertCharacter(characterEntity)
    }

    override suspend fun getCharacter(characterId: Int): CharacterEntity {
      return  applicationDao.getCharacterId(characterId)
    }

    override suspend fun addEpisode(episodeEntity: EpisodeEntity) {
        applicationDao.insertEpisode(episodeEntity)
    }

    override suspend fun getEpisode(episodeId: Int): EpisodeEntity {
        return  applicationDao.getEpisodeId(episodeId)
    }

    override suspend fun addLocation(locationEntity: LocationEntity) {
        applicationDao.getAllLocations()
    }

    override suspend fun getLocation(locationId: Int): LocationEntity {
        return  applicationDao.getLocationId(locationId)
    }
}
