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
import retrofit2.http.Url

interface ApiService {
    @GET("character/")
    suspend fun getCharacters(
        @Query("page") page : Int = BASE_PAGE //TODO убрать этот гвоздь и ниже тк скорее всего не будет работать поиск из-за этого
    ): Response<CharacterDTO>

    @GET("character/{id}")
    suspend fun getOneCharacter(
        @Path("id") id: Int
    ): Response<CharactersResultDTO>

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

    @GET
    suspend fun getCharacterEpisodes(
        @Url url: String
    ): EpisodesResultDTO

    @GET
    suspend fun getLocationResidents(
        @Url url: String
    ): CharactersResultDTO

    @GET
    suspend fun getEpisodeCharacters(
        @Url url: String
    ): CharactersResultDTO

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