package com.essensys.kotlinapplication.Controller

import android.content.Context
import com.essensys.kotlinapplication.Model.ActionLogsData
import com.essensys.kotlinapplication.Model.AssignToUserData
import com.essensys.kotlinapplication.Model.DataWorkList
import com.essensys.kotlinapplication.remote.Constant
import com.essensys.kotlinapplication.remote.RetrofitClient
import com.essensys.kotlinapplication.remote.RetrofitInterfaces
import com.essensys.kotlinapplication.utils.App
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class GetWorkDataController : Callback<ResponseBody> {
    var context:Context?=null
    var listener:WorkDataListener?=null

    constructor(context: Context?, listener: WorkDataListener?)
    {
        this.context = context
        this.listener = listener
    }

    interface WorkDataListener{
        fun onListFetchSuccess(
            dataWorkLists: DataWorkList?,
            actionLogsData: ArrayList<ActionLogsData>?,
            assignToUserData: ArrayList<AssignToUserData>?,
            flagAssigneeVisibility: String?,
            flagStatusVisibility: String?
        )

        fun onListFetchFailure(errorMsg: String?)
    }

    fun getworkData(work_id:String){
        var user_id: String?=null
        var company_id: String?=null
        var user_type: String?=null
        user_id = App.getCurrentUser(context)!!.getUserId()
        company_id = App.getCurrentUser(context)!!.getCompanyId()
        user_type = App.getCurrentUser(context)!!.getUserType()

        RetrofitClient
            .getClient(Constant.ServerEndpoint.GET_WORK_DET)
            .create(RetrofitInterfaces.GetWorkDet::class.java)
            .post(user_id!!, company_id!!, user_type!!, work_id)
            .enqueue(this)
    }

    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
    try {
        var strResp=response.body()!!.string()
        val jsonObject = JSONObject(strResp)
        val result: JSONObject = jsonObject.getJSONObject("result")
        if (result.getString("msg").equals("1", ignoreCase = true)) {
            val flagAssigneeVisibility =
                result.getString("is_visiable_assign_to_dropdown")
            val flagStatusVisibility = result.getString("is_visiable_status_tab")
            val jsonWork = result.getJSONObject("customerDtls")
            val jsonActionLogs = result.getJSONArray("arrWorkDtlsActionLogs")
            val jsonAssignToUser = result.getJSONArray("assignToUserlist")
            val gson = Gson()
            val dataWorkList =
                gson.fromJson(jsonWork.toString(), DataWorkList::class.java)
            val actionLogsDataArrayList = Gson()
                .fromJson<ArrayList<ActionLogsData>>(
                    jsonActionLogs.toString(),
                    object : TypeToken<ArrayList<ActionLogsData?>?>() {}.type
                )
            val assignToUserDataArrayList = Gson()
                .fromJson<ArrayList<AssignToUserData>>(
                    jsonAssignToUser.toString(),
                    object : TypeToken<ArrayList<AssignToUserData>?>() {}.type
                )
            listener!!.onListFetchSuccess(
                dataWorkList,
                actionLogsDataArrayList,
                assignToUserDataArrayList,
                flagAssigneeVisibility,
                flagStatusVisibility
            )
        } else {
            val msg_string = result.getString("msg_string")
            listener!!.onListFetchFailure(msg_string)
        }
    }catch (e:Exception)
    {
        e.printStackTrace()
    }
    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
    }
}