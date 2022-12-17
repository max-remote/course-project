package com.maks.courseproject.ui.fragments.characters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.maks.courseproject.data.network.ApiService
import com.maks.courseproject.data.pagging.characters.CharactersPagingSource
import com.maks.courseproject.data.repositories.DataBaseRepositoryImpl
import com.maks.courseproject.data.repositories.RemoteRepositoryImpl
import com.maks.courseproject.domain.model.characters.CharacterDTO
import com.maks.courseproject.utils.BASE_PAGE
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val apiService: ApiService,
    private val charactersRepository: RemoteRepositoryImpl,
    private val db: DataBaseRepositoryImpl
) : ViewModel() {
    val charactersLiveData: LiveData<CharacterDTO> = MutableLiveData()
    val isLoading: LiveData<Boolean> = MutableLiveData()

    init {
        requestCharacter()
    }

    val listData = Pager(PagingConfig(pageSize = BASE_PAGE)) {
        CharactersPagingSource(apiService = apiService)

    }.flow.cachedIn(viewModelScope)

    fun requestCharacter() {
        viewModelScope.launch {
            try {
                isLoading.mutable().postValue(true)
                charactersRepository.getCharacters().let { response ->
                    if (response.isSuccessful) {
                        isLoading.mutable().postValue(false)
                        charactersLiveData.mutable().postValue(response.body())
                    } else {
                        isLoading.mutable().postValue(false)
                        Log.d("@@@", "Error: ${response.errorBody()}")
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T>
            ?: throw java.lang.IllegalStateException("It is not MutableLiveData o_O")
    }
}
