package com.essensys.kotlinapplication.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.essensys.kotlinapplication.Model.HomeData
import com.essensys.kotlinapplication.Repository.HomeRepo

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    var homeRepo: HomeRepo?=null
    var homeRespLiveData: MutableLiveData<HomeData>?=null
    //used as constructor for primary
    init {
        homeRepo= HomeRepo()
    }

    fun getHomeResponsiveData(user_id: String?, user_type:String?, company_id:String?):MutableLiveData<HomeData>{
       homeRespLiveData=homeRepo!!.getHomeData(user_id,user_type,company_id)
        return homeRespLiveData as MutableLiveData<HomeData>
    }
}