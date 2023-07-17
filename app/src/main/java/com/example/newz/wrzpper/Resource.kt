package com.example.newz.wrzpper

sealed class Resource<T>( val data:T?=null,
                       val message:String?=null){

    class success<T>(data:T):Resource<T>(data)
    class Error<T>(message: String?,data:T?=null):Resource<T>(data, message)
    class Loading<T>:Resource<T>()


}