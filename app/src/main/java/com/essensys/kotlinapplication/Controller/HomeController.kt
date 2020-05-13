package com.essensys.kotlinapplication.Controller

import android.content.Context
import android.widget.Toast
import com.essensys.kotlinapplication.Model.HomeData
import com.essensys.kotlinapplication.remote.Constant
import com.essensys.kotlinapplication.remote.RetrofitClient
import com.essensys.kotlinapplication.remote.RetrofitInterfaces
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeController: Callback<ResponseBody> {
    var mContext: Context?=null
    var listener: HomeController.HomeDataListener?=null

    //secondary constructor
    constructor (mContext: Context, listener: HomeController.HomeDataListener) {
        this.mContext = mContext
        this.listener = listener
    }

    interface HomeDataListener {
        fun onListFetchSuccess(
            mContext: Context?,
            msg: String?,
            msg_string:String?,
            appUrl:String?,
            appVersionCode:String?,
            homeData: HomeData?
        )
        fun onListFetchFailure(errorMsg: String?)
    }

    fun getHomeData(user_id: String?, user_type: String?,company_id:String?)
    {
        RetrofitClient
            .getClient(Constant.ServerEndpoint.GET_HOME_DATA)
            .create(RetrofitInterfaces.GetHomeData::class.java)
            .post(user_id,company_id, user_type)
            .enqueue(this)
    }
    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

    }

    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        try {
            val stringResponse=response.body()!!.string()
            val jsonObject = JSONObject(stringResponse)
            val result = jsonObject.getJSONObject("result")

            val msg = result.getString("msg")
            val msg_string = result.getString("msg_string")
            var appUrl=result.getString("appUrl")
            var appVersionCode=result.getString("appVersionCode")
            val datset = result.getJSONObject("dataSet")

            val gson = Gson()
            val homeData = gson.fromJson<HomeData>(datset.toString(), HomeData::class.java!!)

            listener!!.onListFetchSuccess(mContext,msg,msg_string,appUrl,appVersionCode,homeData);

        } catch (e: Exception) {
            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show()
        }    }
}