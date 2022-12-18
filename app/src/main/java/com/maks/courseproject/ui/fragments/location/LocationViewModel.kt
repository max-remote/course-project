package com.maks.courseproject.ui.fragments.location

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.maks.courseproject.data.network.ApiService
import com.maks.courseproject.data.pagging.locations.LocationsPagingSource
import com.maks.courseproject.data.repositories.RemoteRepositoryImpl
import com.maks.courseproject.domain.model.locations.LocationDTO
import com.maks.courseproject.utils.BASE_PAGE
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val apiService: ApiService,
    private val remoteRepository: RemoteRepositoryImpl
) : ViewModel() {
    val locationsLiveData: LiveData<LocationDTO> = MutableLiveData()
    val isLoading: LiveData<Boolean> = MutableLiveData()


    init {
        requestLocation()
    }

    val listData = Pager(PagingConfig(pageSize = BASE_PAGE)) {
        LocationsPagingSource(apiService = apiService)

    }.flow.cachedIn(viewModelScope)

    fun requestLocation() = viewModelScope.launch {
        isLoading.mutable().postValue(true)
        try {
            remoteRepository.getLocations().let { response ->
                if (response.isSuccessful) {
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
