package com.example.newz.mvmm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class newsviewmodelfactory(val newsrepo: Newsrepo,val application: Application) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if(modelClass.isAssignableFrom(newsviewmodel::class.java)){
            return newsviewmodel(newsrepo,application) as T
        }
        else{
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}