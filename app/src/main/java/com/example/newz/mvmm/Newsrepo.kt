package com.example.newz.mvmm

import androidx.lifecycle.LiveData
import com.example.newz.db.news
import com.example.newz.db.savedarticle
import com.example.newz.service.RetrofitInstance
import retrofit2.Response

class Newsrepo( var newsRepo:newsdao) {
    fun getallsavednews():LiveData<List<savedarticle>>{
       return newsRepo.getall()
    }

    fun getnewsbyid(): LiveData<savedarticle> {
        return newsRepo.getNewsById()
    }
    suspend fun getbreakingnews(country:String,pagenumber:Int): Response<news> {
        return RetrofitInstance.api.getbreakingnews(country,pagenumber)
    }

    suspend fun getcategorynews(category:String): Response<news> {
        return RetrofitInstance.api.geteverything(category)
    }
    suspend fun deleteall(){
        newsRepo.deleteAll()
    }
   suspend  fun insertnews(savedarticle:savedarticle){
        newsRepo.insertNews(savedarticle)
    }
}