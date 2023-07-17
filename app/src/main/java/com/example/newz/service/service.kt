package com.example.newz.service

import com.example.newz.db.news
import com.example.newz.util.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface service {
    @GET("v2/top-headlines")
    suspend fun getbreakingnews(    @Query("country")
                                    country:String="us",
                                     @Query("page")
                                     pageNumber:Int=1,
                                      @Query("apikey")
                                    apikey:String=API_KEY):Response<news>

    @GET("v2/everything")
    suspend fun geteverything(
        @Query("q")
        category:String="",
        @Query("apikey")
        apikey:String= API_KEY):Response<news>



}