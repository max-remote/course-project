package com.maks.courseproject.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.maks.courseproject.data.network.ApiService
import com.maks.courseproject.data.pagging.characters.CharactersPagingSource
import com.maks.courseproject.data.pagging.episodes.EpisodesPagingSource
import com.maks.courseproject.data.pagging.locations.LocationsPagingSource
import com.maks.courseproject.domain.model.characters.CharacterDTO
import com.maks.courseproject.domain.model.characters.CharactersResultDTO
import com.maks.courseproject.domain.model.episodes.EpisodesDTO
import com.maks.courseproject.domain.model.episodes.EpisodesResultDTO
import com.maks.courseproject.domain.model.locations.LocationDTO
import com.maks.courseproject.domain.model.locations.LocationsResultDTO
import com.maks.courseproject.utils.BASE_PAGE
import retrofit2.Response
import javax.inject.Inject

interface RemoteRepository {
    suspend fun getCharacters(name: String): Response<CharacterDTO>
    suspend fun getLocations(name: String): Response<LocationDTO>
    suspend fun getEpisodes(name: String): Response<EpisodesDTO>

    suspend fun getOneCharacter(id: Int): Response<CharactersResultDTO>
    suspend fun getOneEpisode(id: Int): Response<EpisodesResultDTO>
    suspend fun getOneLocation(id: Int): Response<LocationsResultDTO>

    suspend fun getCharacterEpisodes(urls: String): Response<List<EpisodesResultDTO>>
    suspend fun getListOfCharacters(urls: String): Response<List<CharactersResultDTO>>
}

class RemoteRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    RemoteRepository {

    override suspend fun getCharacters(name: String): Response<CharacterDTO> =
        apiService.getCharacters(name = name)

    override suspend fun getLocations(name: String): Response<LocationDTO> =
        apiService.getLocations(name = name)

    override suspend fun getEpisodes(name: String): Response<EpisodesDTO> =
        apiService.getEpisodes(name = name)

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

    fun getSearchResultCharacter(name: String) =
        Pager(
            config = PagingConfig(
                pageSize = BASE_PAGE
            ),
            pagingSourceFactory = { CharactersPagingSource(apiService = apiService, query = name) }
        ).liveData

    fun getSearchResultEpisode(name: String) =
        Pager(
            config = PagingConfig(
                pageSize = BASE_PAGE
            ),
            pagingSourceFactory = { EpisodesPagingSource(apiService = apiService, query = name) }
        ).liveData

    fun getSearchResultLocation(name: String) =
        Pager(
            config = PagingConfig(
                pageSize = BASE_PAGE
            ),
            pagingSourceFactory = { LocationsPagingSource(apiService = apiService, query = name) }
        ).liveData
}

