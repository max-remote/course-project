package com.maks.courseproject.data.pagging.episodes

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maks.courseproject.data.network.ApiService
import com.maks.courseproject.domain.model.episodes.EpisodesResultDTO
import com.maks.courseproject.data.network.utils.BASE_PAGE
import javax.inject.Inject

class EpisodesPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val query: String
) :
    PagingSource<Int, EpisodesResultDTO>() {

    override fun getRefreshKey(state: PagingState<Int, EpisodesResultDTO>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodesResultDTO> {

        return try {
            val currentPage = params.key ?: BASE_PAGE
            val response = apiService.getEpisodes(currentPage, name = query)
            val responseData = mutableListOf<EpisodesResultDTO>()
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == BASE_PAGE) null else - BASE_PAGE,
                nextKey = currentPage.plus(BASE_PAGE)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}