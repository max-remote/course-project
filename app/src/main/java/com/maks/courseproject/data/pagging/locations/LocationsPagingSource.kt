package com.maks.courseproject.data.pagging.locations

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maks.courseproject.data.network.ApiService
import com.maks.courseproject.domain.model.locations.LocationsResultDTO
import javax.inject.Inject

class LocationsPagingSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, LocationsResultDTO>() {

    override fun getRefreshKey(state: PagingState<Int, LocationsResultDTO>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationsResultDTO> {

        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getLocations(currentPage)
            val responseData = mutableListOf<LocationsResultDTO>()
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