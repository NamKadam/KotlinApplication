package com.essensys.kotlinapplication.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.essensys.kotlinapplication.Model.DataWorkList
import com.essensys.kotlinapplication.Repository.WorkListRepo

class WorListViewModel(application: Application) : AndroidViewModel(application) {
    var mWorkListRepo:WorkListRepo?=null
    var worlistrResponsiveLiveData:MutableLiveData<ArrayList<DataWorkList>>?=null
    init {
        mWorkListRepo= WorkListRepo()
    }

    fun getResponsiveLiveData(
        user_id:String?,
        company_id:String?,
        user_type:String?,
        toDate: String?,
        fromDate: String?,
        sr_city: String?,
        sr_route: String?,
        sr_category: String?,
        sr_product: String?,
        offset: String?,
        work_status: String?
    ):MutableLiveData<ArrayList<DataWorkList>>{
      worlistrResponsiveLiveData=mWorkListRepo!!.getWorkListData(user_id,company_id,user_type,toDate,fromDate,sr_city,sr_route,sr_category,sr_product,offset,work_status)
        return worlistrResponsiveLiveData as MutableLiveData<ArrayList<DataWorkList>>
    }
}