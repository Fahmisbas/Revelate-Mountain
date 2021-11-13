package com.revelatestudio.revelatemountain.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.revelatestudio.revelatemountain.data.LatestPicturesPagingSource
import com.revelatestudio.revelatemountain.data.PopularPicturesPagingSource
import com.revelatestudio.revelatemountain.data.remote.PixabayApi
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: PixabayApi
) {

    fun getPopularMountainPictures() = Pager(PagingConfig( pageSize = 20,
        prefetchDistance = 5,
        enablePlaceholders = false,
        initialLoadSize = 20,
                maxSize =60)) {
        PopularPicturesPagingSource(api)
    }.liveData

    fun getLatestMountainPictures() = Pager(PagingConfig(10)) {
        LatestPicturesPagingSource(api)
    }.liveData
}