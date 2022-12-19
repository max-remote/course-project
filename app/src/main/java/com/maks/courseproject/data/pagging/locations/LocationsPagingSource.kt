package com.maks.courseproject.data.pagging.locations

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maks.courseproject.data.network.ApiService
import com.maks.courseproject.domain.model.locations.LocationsResultDTO
import com.maks.courseproject.utils.BASE_PAGE
import javax.inject.Inject

class LocationsPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val query: String
) :
    PagingSource<Int, LocationsResultDTO>() {

    override fun getRefreshKey(state: PagingState<Int, LocationsResultDTO>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationsResultDTO> {

        return try {
            val currentPage = params.key ?: BASE_PAGE
            val response = apiService.getLocations(currentPage,name = query)
            val responseData = mutableListOf<LocationsResultDTO>()
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