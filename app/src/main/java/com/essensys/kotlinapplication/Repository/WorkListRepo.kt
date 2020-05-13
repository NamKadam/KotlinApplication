package com.essensys.kotlinapplication.Repository

import androidx.lifecycle.MutableLiveData
import com.essensys.kotlinapplication.Model.DataWorkList
import com.essensys.kotlinapplication.remote.Constant
import com.essensys.kotlinapplication.remote.RetrofitClient
import com.essensys.kotlinapplication.remote.RetrofitInterfaces
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class WorkListRepo {
    var apiRequest:RetrofitInterfaces.TodaysWorkList?=null

    constructor(){
        apiRequest = RetrofitClient.getClient(Constant.ServerEndpoint.TODAYS_WORKLIST)
            .create(RetrofitInterfaces.TodaysWorkList::class.java)
    }


    fun getWorkListData(
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
    ): MutableLiveData<ArrayList<DataWorkList>> {
        val mData: MutableLiveData<ArrayList<DataWorkList>> =
            MutableLiveData<ArrayList<DataWorkList>>()
        apiRequest!!.post(user_id,company_id,user_type,toDate,fromDate,sr_city,sr_route,sr_category,sr_product,offset,work_status)
            .enqueue(object :Callback<ResponseBody>
            {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if(response.body()!=null)
                    {
                      try{
                          val strResp = response.body()!!.string()
                          val obj = JSONObject(strResp)
                          val result = obj.getJSONObject("result")
                          val arrayCustList=result.getJSONArray("arrCustomerList")

                          val mDataSet = Gson()
                              .fromJson<ArrayList<DataWorkList>>(
                                  arrayCustList.toString(),
                                  object :
                                      TypeToken<ArrayList<DataWorkList?>?>() {}.type
                              )
                          mData.setValue(mDataSet)
                      }catch (e:Exception)
                      {
                          e.printStackTrace()
                          mData.value=null
                      }
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        return mData
    }
}