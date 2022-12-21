package com.maks.courseproject.ui.fragments.location_screens.location_details

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
            try {
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
            } catch (e: Exception) {
                isLoading.mutable().postValue(false)
                Log.d("@@@", "Error: ${e.cause}")
            }
        }
    }

    fun requestResidents(urls: List<String>) {
        viewModelScope.launch {
            try {
                isLoading.mutable().postValue(true)

                val preparedIds: String = urls.joinToString(",") { it.substringAfterLast('/') }
                charactersRepository.getListOfCharacters(preparedIds).let { response ->
                    if (!response.isSuccessful) {
                        isLoading.mutable().postValue(false)
                        Log.d("@@@", "Error")
                    } else {
                        isLoading.mutable().postValue(false)
                        residentsLiveData.mutable().postValue(response.body())
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