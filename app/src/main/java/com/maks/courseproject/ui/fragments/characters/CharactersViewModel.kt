package com.maks.courseproject.ui.fragments.characters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks.courseproject.data.repositories.RemoteRepositoryImpl
import com.maks.courseproject.domain.model.characters.CharacterDTO
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val charactersRepository: RemoteRepositoryImpl
) : ViewModel() {
    val charactersLiveData: LiveData<CharacterDTO> = MutableLiveData()

    init {
      requestCharacter()
    }

    private fun requestCharacter() = viewModelScope.launch {
        charactersRepository.getCharacters().let { response ->
            if (response.isSuccessful) {
                charactersLiveData.mutable().postValue(response.body())
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
