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

    init {
        requestLocation()
    }

    val listData = Pager(PagingConfig(pageSize = BASE_PAGE)) {
        LocationsPagingSource(apiService = apiService)

    }.flow.cachedIn(viewModelScope)

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
            ?: throw IllegalStateException("It is not MutableLiveData o_O")
    }
}
