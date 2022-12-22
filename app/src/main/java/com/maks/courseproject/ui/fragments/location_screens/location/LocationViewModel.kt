package com.maks.courseproject.ui.fragments.location_screens.location

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.maks.courseproject.data.repositories.RemoteRepositoryImpl
import com.maks.courseproject.domain.model.locations.LocationDTO
import com.maks.courseproject.data.network.utils.DEFAULT_STRING_QUERY
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val remoteRepository: RemoteRepositoryImpl
) : ViewModel() {
    val locationsLiveData: LiveData<LocationDTO> = MutableLiveData()
    val isLoading: LiveData<Boolean> = MutableLiveData()
    private val currentQuery = MutableLiveData(DEFAULT_STRING_QUERY)

    init {
        requestLocation(DEFAULT_STRING_QUERY)
    }

    val listData = currentQuery.switchMap { queryString ->
        remoteRepository.getSearchResultLocation(queryString).cachedIn(viewModelScope)
    }

    fun requestLocation(name: String) = viewModelScope.launch {
        isLoading.mutable().postValue(true)
        try {
            remoteRepository.getLocations(name).let { response ->
                if (response.isSuccessful) {
                    currentQuery.value = name
                    locationsLiveData.mutable().postValue(response.body())
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

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T>
            ?: throw IllegalStateException("It is not MutableLiveData o_O")
    }
}
