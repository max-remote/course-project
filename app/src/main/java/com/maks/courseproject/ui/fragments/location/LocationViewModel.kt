package com.maks.courseproject.ui.fragments.location

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maks.courseproject.data.repositories.RemoteRepositoryImpl
import com.maks.courseproject.domain.model.locations.LocationDTO
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val remoteRepository: RemoteRepositoryImpl
) : ViewModel() {
    val locationsLiveData: LiveData<LocationDTO> = MutableLiveData()

    init {
        requestLocation()
    }

    private fun requestLocation() = viewModelScope.launch {
        remoteRepository.getLocations().let { response ->
            if (response.isSuccessful) {
                locationsLiveData.mutable().postValue(response.body())
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
