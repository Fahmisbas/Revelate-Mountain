package com.revelatestudio.revelatemountain.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.revelatestudio.revelatemountain.data.remote.HitsItem
import com.revelatestudio.revelatemountain.data.repository.MainRepository


class MountainPicturesPagingSource(
    private val repository: MainRepository,
    private val order: String
) : PagingSource<Int, HitsItem>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HitsItem> {
        val nextPage = params.key ?: 1
        return try {
            val data = repository.getMountainPictures(
                nextPage = nextPage,
                loadSize = params.loadSize,
                order = order
            )
            LoadResult.Page(
                data,
                if (nextPage == 1) null else nextPage - 1,
                if (nextPage < data.size) nextPage + 1 else null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, HitsItem>): Int? {
        return null
    }


}