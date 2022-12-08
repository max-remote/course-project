package com.maks.courseproject.ui.fragments.episodes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.maks.courseproject.data.network.ApiService
import com.maks.courseproject.data.pagging.episodes.EpisodesPagingSource
import com.maks.courseproject.data.repositories.RemoteRepositoryImpl
import com.maks.courseproject.domain.model.episodes.EpisodesDTO
import com.maks.courseproject.utils.BASE_PAGE
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    private val apiService: ApiService,
    private val remoteRepository: RemoteRepositoryImpl
) : ViewModel() {
    val episodesLiveData: LiveData<EpisodesDTO> = MutableLiveData()

    init {
        requestEpisodes()
    }

    val listData = Pager(PagingConfig(pageSize = BASE_PAGE)) {
        EpisodesPagingSource(apiService = apiService)

    }.flow.cachedIn(viewModelScope)

    private fun requestEpisodes() = viewModelScope.launch {
        remoteRepository.getEpisodes().let { response ->
            if (response.isSuccessful) {
                episodesLiveData.mutable().postValue(response.body())
            } else {
                Log.d("@@@", "getAllCharactersError: ${response.errorBody()}")
            }
        }
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T>
            ?: throw java.lang.IllegalStateException("It is not MutableLiveData o_O")
    }
}