package com.maks.courseproject.data.repositories

import com.maks.courseproject.data.network.ApiService
import com.maks.courseproject.domain.model.characters.CharacterDTO
import com.maks.courseproject.domain.model.episodes.EpisodesDTO
import com.maks.courseproject.domain.model.locations.LocationDTO
import retrofit2.Response
import javax.inject.Inject

interface RemoteRepository {
    suspend fun getCharacters(): Response<CharacterDTO>
    suspend fun getLocations(): Response<LocationDTO>
    suspend fun getEpisodes(): Response<EpisodesDTO>
}

class RemoteRepositoryImpl @Inject constructor(private val apiService: ApiService) : RemoteRepository {

    override suspend fun getCharacters(): Response<CharacterDTO> = apiService.getCharacters()

    override suspend fun getLocations(): Response<LocationDTO> = apiService.getLocations()

    override suspend fun getEpisodes(): Response<EpisodesDTO> = apiService.getEpisodes()

}
