package com.maks.courseproject.data.pagging.characters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maks.courseproject.data.network.ApiService
import com.maks.courseproject.domain.model.characters.CharactersResultDTO
import javax.inject.Inject

class CharactersPagingSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, CharactersResultDTO>() {

    override fun getRefreshKey(state: PagingState<Int, CharactersResultDTO>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersResultDTO> {

        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getCharacters(currentPage)
            val responseData = mutableListOf<CharactersResultDTO>()
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