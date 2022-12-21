package com.maks.courseproject.data.network

import com.maks.courseproject.domain.model.characters.CharacterDTO
import com.maks.courseproject.domain.model.characters.CharactersResultDTO
import com.maks.courseproject.domain.model.episodes.EpisodesDTO
import com.maks.courseproject.domain.model.episodes.EpisodesResultDTO
import com.maks.courseproject.domain.model.locations.LocationDTO
import com.maks.courseproject.domain.model.locations.LocationsResultDTO
import com.maks.courseproject.utils.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("character/")
    suspend fun getCharacters(
        @Query("page") page : Int = BASE_PAGE,
        @Query("name") name : String = DEFAULT_STRING_QUERY,
    ): Response<CharacterDTO>

    @GET("character/")
    suspend fun getFilterCharacters(
        @Query("page") page : Int = BASE_PAGE,
        @Query("status") status : String = DEFAULT_STRING_QUERY,
        @Query("species") species : String = DEFAULT_STRING_QUERY,
        @Query("type") type : String = DEFAULT_STRING_QUERY,
        @Query("gender") gender : String = DEFAULT_STRING_QUERY
    ): Response<CharacterDTO>

    @GET("character/{id}")
    suspend fun getOneCharacter(
        @Path("id") id: Int
    ): Response<CharactersResultDTO>

    @GET("character/[{urlsId}]")
    suspend fun getListOfCharactersForDetails(
        @Path("urlsId") urlsId: String
    ): Response<List<CharactersResultDTO>>

    @GET("location/")
    suspend fun getLocations(
        @Query("page") page: Int = BASE_PAGE,
        @Query("name") name : String = DEFAULT_STRING_QUERY
    ): Response<LocationDTO>

    @GET("location/{id}")
    suspend fun getOneLocation(
        @Path("id") id: Int
    ): Response<LocationsResultDTO>

    @GET("episode/")
    suspend fun getEpisodes(
        @Query("page") page: Int = BASE_PAGE,
        @Query("name") name : String = DEFAULT_STRING_QUERY
    ): Response<EpisodesDTO>

    @GET("episode/{id}")
    suspend fun getOneEpisode(
        @Path("id") id: Int
    ): Response<EpisodesResultDTO>

    @GET("episode/[{urlsId}]")
    suspend fun getCharacterEpisodes(
        @Path("urlsId") urlsId: String
    ): Response <List<EpisodesResultDTO>>

    companion object {
        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .setClient()
                .addJsonConverter()
                .build()
                .create(ApiService::class.java)
        }
    }
}