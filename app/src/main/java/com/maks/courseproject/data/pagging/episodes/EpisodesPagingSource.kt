package com.maks.courseproject.data.pagging.episodes

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maks.courseproject.data.network.ApiService
import com.maks.courseproject.domain.model.episodes.EpisodesResultDTO
import javax.inject.Inject

class EpisodesPagingSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, EpisodesResultDTO>() {

    override fun getRefreshKey(state: PagingState<Int, EpisodesResultDTO>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodesResultDTO> {

        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getEpisodes(currentPage)
            val responseData = mutableListOf<EpisodesResultDTO>()
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}