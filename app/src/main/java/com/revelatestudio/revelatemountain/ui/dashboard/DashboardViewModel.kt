package com.revelatestudio.revelatemountain.ui.dashboard


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.revelatestudio.revelatemountain.data.MountainPicturesPagingSource
import com.revelatestudio.revelatemountain.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    fun getPopularMountainPicturesPager() = Pager(
        PagingConfig(
            pageSize = 20,
            prefetchDistance = 5,
            enablePlaceholders = false,
            initialLoadSize = 20,
            maxSize = 60
        )
    ) {
        MountainPicturesPagingSource(repository, POPULAR_CATEGORY)
    }.liveData.cachedIn(viewModelScope)


    fun getLatestMountainPictures() = Pager(
        PagingConfig(
            pageSize = 20,
            prefetchDistance = 5,
            enablePlaceholders = false,
            initialLoadSize = 20,
            maxSize = 60
        )
    ) {
        MountainPicturesPagingSource(repository, LATEST_CATEGORY)
    }.liveData.cachedIn(viewModelScope)


    companion object {
        const val POPULAR_CATEGORY = "popular"
        const val LATEST_CATEGORY = "latest"
    }


}