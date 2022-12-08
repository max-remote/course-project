package com.maks.courseproject.ui.fragments.episodes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks.courseproject.data.repositories.RemoteRepositoryImpl
import com.maks.courseproject.domain.model.episodes.EpisodesDTO
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    private val remoteRepository: RemoteRepositoryImpl
) : ViewModel() {
    val episodesLiveData: LiveData<EpisodesDTO> = MutableLiveData()

    init {
        requestEpisodes()
    }

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