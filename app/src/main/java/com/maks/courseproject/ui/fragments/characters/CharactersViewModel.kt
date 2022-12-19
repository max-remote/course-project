package com.maks.courseproject.ui.fragments.characters

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.maks.courseproject.data.repositories.RemoteRepositoryImpl
import com.maks.courseproject.domain.model.characters.CharacterDTO
import com.maks.courseproject.utils.DEFAULT_QUERY
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val charactersRepository: RemoteRepositoryImpl
) : ViewModel() {
    val charactersLiveData: LiveData<CharacterDTO> = MutableLiveData()
    val isLoading: LiveData<Boolean> = MutableLiveData()
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    init {
        requestCharacter(DEFAULT_QUERY)
    }

    val listData = currentQuery.switchMap { queryString ->
        charactersRepository.getSearchResultCharacter(queryString).cachedIn(viewModelScope)
    }

    fun requestCharacter(name: String) {
        viewModelScope.launch {
            try {
                isLoading.mutable().postValue(true)
                charactersRepository.getCharacters(name).let { response ->
                    if (response.isSuccessful) {
                        currentQuery.value = name
                        charactersLiveData.mutable().postValue(response.body())
                        isLoading.mutable().postValue(false)
                    } else {
                        isLoading.mutable().postValue(false)
                        Log.d("@@@", "Error: ${response.errorBody()}")
                    }
                }
            } catch (e: Exception) {
                isLoading.mutable().postValue(false)
                Log.d("@@@", "Error: ${e.cause}")
            }
        }
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T>
            ?: throw java.lang.IllegalStateException("It is not MutableLiveData o_O")
    }
}
