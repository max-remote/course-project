package com.maks.courseproject.ui.fragments.location_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks.courseproject.data.repositories.RemoteRepositoryImpl
import com.maks.courseproject.domain.model.characters.CharactersResultDTO
import com.maks.courseproject.domain.model.locations.LocationsResultDTO
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsLocationViewModel @Inject constructor(
    private val charactersRepository: RemoteRepositoryImpl
) : ViewModel() {

    val isLoading: LiveData<Boolean> = MutableLiveData()
    val locationLiveData: LiveData<LocationsResultDTO> = MutableLiveData()

    val residentsLiveData: LiveData<List<CharactersResultDTO>> = MutableLiveData()

    fun requestLocation(characterId: Int) {
        viewModelScope.launch {
            isLoading.mutable().postValue(true)
            charactersRepository.getOneLocation(characterId).let { response ->
                if (response.isSuccessful) {
                    isLoading.mutable().postValue(false)
                    locationLiveData.mutable().postValue(response.body())
                } else {
                    isLoading.mutable().postValue(false)
                    Log.d("@@@", "Error: ${response.errorBody()}")
                }
            }
        }
    }

    fun requestResidents(urls: List<String>) {
        viewModelScope.launch {
            isLoading.mutable().postValue(true)
            charactersRepository.getLocationResidents(urls).let { response ->
                if (response.isEmpty()) {
                    isLoading.mutable().postValue(false)
                    Log.d("@@@", "Error")
                } else {
                    isLoading.mutable().postValue(false)
                    residentsLiveData.mutable().postValue(response)
                }
            }
        }
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T>
            ?: throw java.lang.IllegalStateException("It is not MutableLiveData o_O")
    }
}