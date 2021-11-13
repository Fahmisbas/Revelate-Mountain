package com.revelatestudio.revelatemountain.data.repository

import androidx.paging.DataSource
import com.revelatestudio.revelatemountain.data.remote.PixabayResponse
import com.revelatestudio.revelatemountain.util.Resource

interface IRepository {

    suspend fun getPopularMountainPictures() : DataSource.Factory<Int, PixabayResponse>
    suspend fun getLatestMountainPictures() : Resource<PixabayResponse>


}