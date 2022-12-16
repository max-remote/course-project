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

    suspend fun getCharacterEpisodes(urls: String): Response<List<EpisodesResultDTO>>
    suspend fun getListOfCharacters(urls: String): Response<List<CharactersResultDTO>>
}

class RemoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    ) :
    RemoteRepository {

    override suspend fun getCharacters(): Response<CharacterDTO> = apiService.getCharacters()

    override suspend fun getLocations(): Response<LocationDTO> = apiService.getLocations()

    override suspend fun getEpisodes(): Response<EpisodesDTO> = apiService.getEpisodes()

    override suspend fun getOneCharacter(id: Int): Response<CharactersResultDTO> =
        apiService.getOneCharacter(id = id)

    override suspend fun getOneLocation(id: Int): Response<LocationsResultDTO> =
        apiService.getOneLocation(id = id)

    override suspend fun getOneEpisode(id: Int): Response<EpisodesResultDTO> =
        apiService.getOneEpisode(id = id)

    override suspend fun getCharacterEpisodes(urls: String): Response<List<EpisodesResultDTO>> =
        apiService.getCharacterEpisodes(urlsId = urls)

    override suspend fun getListOfCharacters(urls: String): Response<List<CharactersResultDTO>> =
        apiService.getListOfCharactersForDetails(urlsId = urls)
}
