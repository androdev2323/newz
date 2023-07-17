package com.example.newz.mvmm

import android.content.Context
import androidx.room.*
import com.example.newz.db.Classconvertor
import com.example.newz.db.news
import com.example.newz.db.savedarticle

@Database(entities = [savedarticle::class], version = 1)
@TypeConverters(Classconvertor::class)
abstract class Newsdatabase: RoomDatabase() {
    abstract fun getdao():newsdao

    companion object{
        private var INSTANCE:Newsdatabase?=null
        public fun getdatabase(context:Context):Newsdatabase{
            synchronized(this) {
                if (INSTANCE == null) {
                   INSTANCE=Room.databaseBuilder(context.applicationContext,Newsdatabase::class.java,"NEWSARTICLE").build()
                }
            }
            return INSTANCE!!
        }
    }
}