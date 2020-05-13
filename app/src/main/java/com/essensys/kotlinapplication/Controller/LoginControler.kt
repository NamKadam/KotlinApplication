package com.essensys.kotlinapplication.Controller

import android.content.Context
import android.widget.Toast
import com.essensys.kotlinapplication.Model.User
import com.essensys.kotlinapplication.remote.Constant
import com.essensys.kotlinapplication.remote.RetrofitClient
import com.essensys.kotlinapplication.remote.RetrofitInterfaces
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginControler : Callback<ResponseBody> {
     var mContext:Context?=null
     var listener:StatusListener?=null

    //secondary constructor
    constructor (mContext: Context, listener:StatusListener) {
        this.mContext = mContext
        this.listener = listener
    }

    interface StatusListener {
        fun onListFetchSuccess(
            mContext: Context?,
            msg: String?,
            loginData: User
        )
        fun onListFetchFailure(errorMsg: String?)
    }
    //webservice for login
   fun doLogin(username: String, password: String)
    {
        RetrofitClient
            .getClient(Constant.ServerEndpoint.LOGIN)
            .create(RetrofitInterfaces.DoLogin::class.java)
            .post(username, password)
            .enqueue(this)
    }

    //webservice for forgotPass
  fun forgotPassword(email: String)
    {
        RetrofitClient
            .getClient(Constant.ServerEndpoint.FORGOT_PASSWORD)
            .create(RetrofitInterfaces.ForgotPassword::class.java)
            .post(email)
            .enqueue(this)
    }

    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        try {
            val stringResponse=response.body()!!.string()
            val jsonObject = JSONObject(stringResponse)
            val result = jsonObject.getJSONObject("result")

            val gson = Gson()
            val loginData = gson.fromJson<User>(result.toString(), User::class.java)

            val msg = result.getString("msg")
            val msg_string = result.getString("msg_string")
            if (msg.contentEquals("1")) {
//                Toast.makeText(mContext, msg_string, Toast.LENGTH_SHORT).show()
//                try {
//                    SharedPreferenceManager.with(mContext!!).updateLoggedInUser(loginData)
//
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
               listener!!.onListFetchSuccess(mContext,"1",loginData);
//                startActivity(Intent(this, HomeActivity::class.java))
//                finish()
            } else {
                Toast.makeText(mContext, msg_string, Toast.LENGTH_SHORT).show()
                //                startActivity(new Intent(this,HomeActivity.class));
            }

        } catch (e: Exception) {
            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

    }
}