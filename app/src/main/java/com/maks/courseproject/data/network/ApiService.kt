package com.maks.courseproject.data.network

import com.maks.courseproject.domain.model.characters.CharacterDTO
import com.maks.courseproject.domain.model.characters.CharactersResultDTO
import com.maks.courseproject.domain.model.episodes.EpisodesDTO
import com.maks.courseproject.domain.model.episodes.EpisodesResultDTO
import com.maks.courseproject.domain.model.locations.LocationDTO
import com.maks.courseproject.domain.model.locations.LocationsResultDTO
import com.maks.courseproject.utils.BASE_PAGE
import com.maks.courseproject.utils.BASE_URL
import com.maks.courseproject.utils.addJsonConverter
import com.maks.courseproject.utils.setClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("character/")
    suspend fun getCharacters(
        @Query("page") page : Int = BASE_PAGE
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
        @Query("page") page: Int = BASE_PAGE
    ): Response<LocationDTO>

    @GET("location/{id}")
    suspend fun getOneLocation(
        @Path("id") id: Int
    ): Response<LocationsResultDTO>

    @GET("episode/")
    suspend fun getEpisodes(
        @Query("page") page: Int = BASE_PAGE
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