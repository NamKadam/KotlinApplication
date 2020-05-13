package com.essensys.kotlinapplication.Repository

import androidx.lifecycle.MutableLiveData
import com.essensys.kotlinapplication.Model.HomeData
import com.essensys.kotlinapplication.Model.User
import com.essensys.kotlinapplication.remote.Constant
import com.essensys.kotlinapplication.remote.RetrofitClient
import com.essensys.kotlinapplication.remote.RetrofitInterfaces
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class HomeRepo {
    var apiRequest: RetrofitInterfaces.GetHomeData? = null

    //secondaryconstructor
    constructor() {
        apiRequest = RetrofitClient.getClient(Constant.ServerEndpoint.GET_HOME_DATA)
            .create(RetrofitInterfaces.GetHomeData::class.java)
    }

    fun getHomeData(
        user_id: String?,
        user_type: String?,
        company_id: String?
    ): MutableLiveData<HomeData> {
        val data = MutableLiveData<HomeData>()
        apiRequest!!.post(user_id, user_type, company_id)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.body() != null)
                        try {
//                                Log.e("====strResponse===","===strResponse===="+response.body().string());
                            val strResp = response.body()!!.string()
                            val obj = JSONObject(strResp)
                            val obj2 = obj.getJSONObject("result")
                            val dataset = obj2.getJSONObject("dataSet")
                            val homedata = Gson().fromJson(
                                dataset.toString(),
                                HomeData::class.java
                            )
                            //LoginResponse loginResponse = new Gson().fromJson(response.body().string(), LoginResponse.class);
                            // data.setValue(response.body().string());
                            data.setValue(homedata)
                        } catch (e: IOException) {
                            e.printStackTrace()
                            data.setValue(null)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            data.setValue(null)
                        }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        return data

    }

}