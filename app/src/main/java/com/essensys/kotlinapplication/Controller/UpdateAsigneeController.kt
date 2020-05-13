package com.essensys.kotlinapplication.Controller

import android.content.Context
import com.essensys.kotlinapplication.remote.Constant
import com.essensys.kotlinapplication.remote.RetrofitClient
import com.essensys.kotlinapplication.remote.RetrofitInterfaces
import com.essensys.kotlinapplication.utils.App
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateAsigneeController : Callback<ResponseBody> {
    var mContext:Context?=null
    var listener:UpdateAsigneeListener?=null

    constructor(mContext:Context,listener:UpdateAsigneeListener)
    {
        this.mContext=mContext
        this.listener=listener
    }

    interface UpdateAsigneeListener{
        fun onUpdateAsigneeSuccess(msg:String,msg_string:String)
        fun onUpdateAsigneeFailure(errorMsg:String)
    }
    fun updateAsignee(work_id:String,assignedUserId:String){
        val user_id: String
        val company_id: String
        val user_type: String
        user_id = App.getCurrentUser(mContext)!!.getUserId().toString()
        company_id = App.getCurrentUser(mContext)!!.getCompanyId().toString()
        user_type = App.getCurrentUser(mContext)!!.getUserType().toString()

        RetrofitClient.getClient(Constant.ServerEndpoint.UPDATE_ASSIGNEE)
            .create(RetrofitInterfaces.UpdateAssignee::class.java)
            .post(user_id, user_type, company_id, work_id, assignedUserId)
            .enqueue(this)
    }

    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
    }
    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
    }
}