package com.example.newz.db

import androidx.room.TypeConverter

class Classconvertor {
    @TypeConverter
    fun tosourceid(string:String):Source{
        return Source(string,string)
    }
     @TypeConverter
    fun fromsourcename(source:Source):String{
        return source.name!!
    }
}