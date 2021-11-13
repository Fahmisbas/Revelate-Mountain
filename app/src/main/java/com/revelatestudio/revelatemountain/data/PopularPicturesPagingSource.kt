package com.revelatestudio.revelatemountain.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.revelatestudio.revelatemountain.data.remote.HitsItem
import com.revelatestudio.revelatemountain.data.remote.PixabayApi


class PopularPicturesPagingSource(
    private val api: PixabayApi
) : PagingSource<Int, HitsItem>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HitsItem> {

        return try {
            val nextPage = params.key ?: 1
            val response = api.getMountainPictures(page = nextPage, perPage = params.loadSize)?.hits

            Log.i("goblok", nextPage.toString())

            LoadResult.Page(data = response!!,
                if (nextPage == 1) null else nextPage - 1,
                if (nextPage < response.size) nextPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }



    companion object {

    }

    override fun getRefreshKey(state: PagingState<Int, HitsItem>): Int? {
       return null
    }


}