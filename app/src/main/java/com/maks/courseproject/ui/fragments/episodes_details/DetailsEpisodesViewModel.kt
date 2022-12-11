package com.maks.courseproject.ui.fragments.episodes_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks.courseproject.data.repositories.RemoteRepositoryImpl
import com.maks.courseproject.domain.model.episodes.EpisodesResultDTO
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsEpisodesViewModel @Inject constructor(
    private val charactersRepository: RemoteRepositoryImpl
) : ViewModel() {

    val isLoading: LiveData<Boolean> = MutableLiveData()
    val episodesLiveData: LiveData<EpisodesResultDTO> = MutableLiveData()

    fun requestEpisode(characterId: Int) {
        viewModelScope.launch {
            isLoading.mutable().postValue(true)
            charactersRepository.getOneEpisode(characterId).let { response ->
                if (response.isSuccessful) {
                    isLoading.mutable().postValue(false)
                    episodesLiveData.mutable().postValue(response.body())
                } else {
                    isLoading.mutable().postValue(false)
                    Log.d("@@@", "Error: ${response.errorBody()}")
                }
            }
        }
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T>
            ?: throw java.lang.IllegalStateException("It is not MutableLiveData o_O")
    }
}