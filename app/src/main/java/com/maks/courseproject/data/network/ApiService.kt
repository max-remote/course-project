package com.maks.courseproject.data.network

import com.maks.courseproject.domain.model.characters.CharacterDTO
import com.maks.courseproject.domain.model.episodes.EpisodesDTO
import com.maks.courseproject.domain.model.locations.LocationDTO
import com.maks.courseproject.utils.BASE_URL
import com.maks.courseproject.utils.addJsonConverter
import com.maks.courseproject.utils.setClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("character/")
    suspend fun getCharacters(
        @Query("page") page : Int = 1
    ): Response<CharacterDTO>

    @GET("location")
    suspend fun getLocations(): Response<LocationDTO>

    @GET("episode")
    suspend fun getEpisodes(): Response<EpisodesDTO>

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