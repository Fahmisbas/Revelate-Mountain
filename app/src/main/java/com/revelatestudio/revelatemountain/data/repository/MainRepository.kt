package com.revelatestudio.revelatemountain.data.repository


import com.revelatestudio.revelatemountain.data.remote.HitsItem
import com.revelatestudio.revelatemountain.data.remote.PixabayApi
import com.revelatestudio.revelatemountain.util.API_KEY
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val api: PixabayApi
) {

    suspend fun getMountainPictures(nextPage : Int, loadSize : Int, order : String) : List<HitsItem> {
        val response = api.getMountainPictures(page = nextPage, perPage = loadSize, order = order)
        val result = response?.hits
        return if (!result.isNullOrEmpty()) {
            result
        } else listOf()
    }
}