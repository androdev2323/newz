package com.example.newz.mvmm


import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import com.example.newz.db.news
import com.example.newz.db.savedarticle
import com.example.newz.wrzpper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class newsviewmodel(val newsrepo: Newsrepo,application:Application):AndroidViewModel(application) {


 val breakingnews:MutableLiveData<Resource<news>> = MutableLiveData()
    val categorynews:MutableLiveData<Resource<news>> = MutableLiveData()
    val page=1

    init {
        getbreakingnews("us")
    }

    var savednews=newsrepo.getallsavednews()

 fun getbreakingnews(code:String){
    viewModelScope.launch(Dispatchers.IO) {
        checkinternetandbreakingnews(code)
    }
}

 fun getcategorynews(cat:String){
    categorynews.postValue(Resource.Loading())
    viewModelScope.launch(Dispatchers.IO) {
        var response= newsrepo.getcategorynews(cat)
        categorynews.postValue(handleresponse(response))
    }
}

    fun Deleteall(){
        viewModelScope.launch {
            newsrepo.deleteall()
        }
    }
    fun insertnews(savedarticle: savedarticle){
        viewModelScope.launch {

            newsrepo.insertnews(savedarticle)
        }
    }






    private suspend fun checkinternetandbreakingnews(code:String){
        breakingnews.postValue(Resource.Loading())
        try {

            if(hasInternetConnection()){
                var response=newsrepo.getbreakingnews(code,page)
                breakingnews.postValue(handleresponse(response))
            }
            else{
                breakingnews.postValue(Resource.Error("No internet Connection"))
            }
        }
        catch(t:Throwable){
            when(t){
                is IOException ->{
                    breakingnews.postValue(Resource.Error("an Io exception ocurred"))
                }
                else -> breakingnews.postValue(Resource.Error(t.message))
            }

        }

    }
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<newsapplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
    private fun handleresponse(response: Response<news>): Resource<news>? {
        if(response.isSuccessful){
            response.body()?.let {
                    resultresponse->
                return Resource.success(resultresponse)
            }


        }


        return   return Resource.success(response.body()!!)
    }

}