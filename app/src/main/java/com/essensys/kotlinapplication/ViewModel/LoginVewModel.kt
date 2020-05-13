package com.essensys.kotlinapplication.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.essensys.kotlinapplication.Model.User
import com.essensys.kotlinapplication.Repository.LoginRepo

class LoginVewModel(application: Application) : AndroidViewModel(application) {

    var loginRepository:LoginRepo?=null
    var loginRespLiveData:MutableLiveData<User>?=null
    //used as constructor for primary
    init {
        loginRepository= LoginRepo()
    }

   suspend fun getLoginResponseLiveData(email:String, password:String):MutableLiveData<User>
    {
        loginRespLiveData=loginRepository!!.checkLoginDetails(email,password)
        return loginRespLiveData as MutableLiveData<User>
    }

}