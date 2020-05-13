package com.essensys.kotlinapplication.Repository

import androidx.lifecycle.MutableLiveData
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

//object is used for singleton class
class LoginRepo {
    var apiRequest:RetrofitInterfaces.DoLogin?=null;

    //secondaryconstructor
    constructor() {
        apiRequest = RetrofitClient.getClient(Constant.ServerEndpoint.LOGIN)
            .create(RetrofitInterfaces.DoLogin::class.java)
    }
    suspend fun checkLoginDetails(email:String,password:String):MutableLiveData<User>{

        val data = MutableLiveData<User>()
        apiRequest!!.post(email,password)
            .enqueue(object:Callback<ResponseBody>
            {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.body()!=null)
                        try {
//                                Log.e("====strResponse===","===strResponse===="+response.body().string());
                            val strResp = response.body()!!.string()
                            val obj = JSONObject(strResp)
                            val obj2 = obj.getJSONObject("result")
                            val user = Gson().fromJson(
                                obj2.toString(),
                                User::class.java
                            )
                            //LoginResponse loginResponse = new Gson().fromJson(response.body().string(), LoginResponse.class);
                            // data.setValue(response.body().string());
                            data.setValue(user)
                        } catch (e: IOException) {
                            e.printStackTrace()
                            data.setValue(null)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            data.setValue(null)
                        }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                }
            })

        return data

    }


}