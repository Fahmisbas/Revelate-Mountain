package com.revelatestudio.revelatemountain.data.remote

import com.revelatestudio.revelatemountain.util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PixabayApi {

    @GET("?key=$API_KEY")
    suspend fun getMountainPictures(
        @Query("q") keyword : String = "mountain",
        @Query("image_type") imageType : String = "photo",
        @Query("pretty") pretty : String = "true",
        @Query("orientation") orientation : String  = "vertical",
        @Query("order") order : String?,
        @Query("page") page : Int?,
        @Query("per_page") perPage : Int?
    ) : PixabayResponse?
}