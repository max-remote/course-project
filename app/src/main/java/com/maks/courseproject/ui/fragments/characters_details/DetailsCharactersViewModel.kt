package com.maks.courseproject.ui.fragments.characters_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks.courseproject.data.repositories.RemoteRepositoryImpl
import com.maks.courseproject.domain.model.characters.CharactersResultDTO
import com.maks.courseproject.domain.model.episodes.EpisodesDTO
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsCharactersViewModel @Inject constructor(
    private val charactersRepository: RemoteRepositoryImpl
) : ViewModel() {

    val isLoading: LiveData<Boolean> = MutableLiveData()
    val charactersLiveData: LiveData<CharactersResultDTO> = MutableLiveData()

    val episodesLiveData: LiveData<EpisodesDTO> = MutableLiveData()

    fun requestCharacter(characterId: Int) {
        viewModelScope.launch {
            isLoading.mutable().postValue(true)
            charactersRepository.getOneCharacter(characterId).let { response ->
                if (response.isSuccessful) {
                    isLoading.mutable().postValue(false)
                    charactersLiveData.mutable().postValue(response.body())
                } else {
                    isLoading.mutable().postValue(false)
                    Log.d("@@@", "getAllCharactersError: ${response.errorBody()}")
                }
            }
        }
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T>
            ?: throw java.lang.IllegalStateException("It is not MutableLiveData o_O")
    }
}