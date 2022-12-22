package com.maks.courseproject.data.pagging.characters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maks.courseproject.data.network.ApiService
import com.maks.courseproject.domain.model.characters.CharactersResultDTO
import com.maks.courseproject.data.network.utils.BASE_PAGE
import javax.inject.Inject

class CharactersPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val query: String
) :
    PagingSource<Int, CharactersResultDTO>() {

    override fun getRefreshKey(state: PagingState<Int, CharactersResultDTO>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersResultDTO> {

        return try {
            val currentPage = params.key ?: BASE_PAGE
            val response = apiService.getCharacters(currentPage, name = query)
            val responseData = mutableListOf<CharactersResultDTO>()
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