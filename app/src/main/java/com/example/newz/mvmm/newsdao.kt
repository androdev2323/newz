package com.example.newz.mvmm

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newz.db.savedarticle

@Dao
interface newsdao {
    @Insert
   suspend fun insertNews(savedarticle: savedarticle)

    @Query("DELETE FROM NEWSARTICLE")
  suspend  fun deleteAll()

    @Query("SELECT * FROM NEWSARTICLE")
    fun getall():LiveData<List<savedarticle>>

    @Query("SELECT * FROM NEWSARTICLE")
    fun getNewsById(): LiveData<savedarticle>




}