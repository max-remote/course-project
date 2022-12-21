package com.maks.courseproject.ui.fragments.episodes_screens.episodes

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.maks.courseproject.data.repositories.RemoteRepositoryImpl
import com.maks.courseproject.domain.model.episodes.EpisodesDTO
import com.maks.courseproject.utils.DEFAULT_STRING_QUERY
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    private val remoteRepository: RemoteRepositoryImpl
) : ViewModel() {
    val episodesLiveData: LiveData<EpisodesDTO> = MutableLiveData()
    val isLoading: LiveData<Boolean> = MutableLiveData()
    private val currentQuery = MutableLiveData(DEFAULT_STRING_QUERY)

    init {
        requestEpisodes(DEFAULT_STRING_QUERY)
    }

    val listData = currentQuery.switchMap { queryString ->
        remoteRepository.getSearchResultEpisode(queryString).cachedIn(viewModelScope)
    }

    fun requestEpisodes(name: String) = viewModelScope.launch {
        try {
            isLoading.mutable().postValue(true)
            remoteRepository.getEpisodes(name).let { response ->
                if (response.isSuccessful) {
                    currentQuery.value = name
                    episodesLiveData.mutable().postValue(response.body())
                    isLoading.mutable().postValue(false)
                } else {
                    Log.d("@@@", "Error: ${response.errorBody()}")
                    isLoading.mutable().postValue(false)
                }
            }
        } catch (e: Exception) {
            isLoading.mutable().postValue(false)
            Log.d("@@@", "Error: ${e.cause}")
        }
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T>
            ?: throw java.lang.IllegalStateException("It is not MutableLiveData o_O")
    }
}