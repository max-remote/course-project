package com.maks.courseproject.data.repositories

import com.maks.courseproject.data.network.ApiService
import com.maks.courseproject.domain.model.characters.CharacterDTO
import com.maks.courseproject.domain.model.characters.CharactersResultDTO
import com.maks.courseproject.domain.model.episodes.EpisodesDTO
import com.maks.courseproject.domain.model.episodes.EpisodesResultDTO
import com.maks.courseproject.domain.model.locations.LocationDTO
import com.maks.courseproject.domain.model.locations.LocationsResultDTO
import retrofit2.Response
import javax.inject.Inject

interface RemoteRepository {
    suspend fun getCharacters(): Response<CharacterDTO>
    suspend fun getLocations(): Response<LocationDTO>
    suspend fun getEpisodes(): Response<EpisodesDTO>

    suspend fun getOneCharacter(id: Int): Response<CharactersResultDTO>
    suspend fun getOneEpisode(id: Int): Response<EpisodesResultDTO>
    suspend fun getOneLocation(id: Int): Response<LocationsResultDTO>

    suspend fun getCharacterEpisodes(urls: List<String>): List<EpisodesResultDTO>
    suspend fun getLocationResidents(urls: List<String>): List<CharactersResultDTO>
    suspend fun getEpisodeCharacters(urls: List<String>): List<CharactersResultDTO>
}

class RemoteRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    RemoteRepository {

    override suspend fun getCharacters(): Response<CharacterDTO> = apiService.getCharacters()

    override suspend fun getLocations(): Response<LocationDTO> = apiService.getLocations()

    override suspend fun getEpisodes(): Response<EpisodesDTO> = apiService.getEpisodes()

    override suspend fun getOneCharacter(id: Int): Response<CharactersResultDTO> =
        apiService.getOneCharacter(id = id)

    override suspend fun getOneLocation(id: Int): Response<LocationsResultDTO> =
        apiService.getOneLocation(id = id)

    override suspend fun getCharacterEpisodes(urls: List<String>) =
        urls.map { url ->
            apiService.getCharacterEpisodes(url)
        }

    override suspend fun getLocationResidents(urls: List<String>) =
        urls.map { url ->
            apiService.getLocationResidents(url)
        }

    override suspend fun getEpisodeCharacters(urls: List<String>) =
        urls.map { url ->
            apiService.getEpisodeCharacters(url)
        }

    override suspend fun getOneEpisode(id: Int): Response<EpisodesResultDTO> =
        apiService.getOneEpisode(id = id)
}
