package com.essensys.kotlinapplication.Controller

import android.content.Context
import com.essensys.kotlinapplication.Model.StorageFile
import com.essensys.kotlinapplication.remote.Constant
import com.essensys.kotlinapplication.remote.RetrofitClient
import com.essensys.kotlinapplication.remote.RetrofitInterfaces
import com.essensys.kotlinapplication.utils.App
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class UpdateWorkController : Callback<ResponseBody> {
    var mContext: Context? = null
    var listener: UpdateWorkListener? = null

    constructor(mContext: Context, listener: UpdateWorkListener) {
        this.mContext = mContext
        this.listener = listener

    }

    interface UpdateWorkListener {
        fun onUpdateFetchSuccess(
            msg: String, msg_string: String
        )

        fun onUpdateFetchFailure(errorMsg: String?)
    }

    fun updateWork(
        workId: String?,
        workStatus: String,
        assignedUserId: String?,
        earn: String,
        expenses: String,
        comment: String,
        dateCallSchedule: String,
        stringArrayListImage: ArrayList<StorageFile>
    ) {
        val user_id: String
        val company_id: String
        val user_type: String
        user_id = App.getCurrentUser(mContext)!!.getUserId().toString()
        company_id = App.getCurrentUser(mContext)!!.getCompanyId().toString()
        user_type = App.getCurrentUser(mContext)!!.getUserType().toString()

        val update = HashMap<String, RequestBody>()
        update["user_id"] =
            RetrofitClient.getRequestBodyFromString(user_id)
        update["company_id"] =
            RetrofitClient.getRequestBodyFromString(company_id)
        update["user_type"] =
            RetrofitClient.getRequestBodyFromString(user_type)

        update["work_id"] = RetrofitClient.getRequestBodyFromString(workId)
        update["work_status"] = RetrofitClient.getRequestBodyFromString(workStatus)
        update["assignedToUserId"] = RetrofitClient.getRequestBodyFromString(assignedUserId)
        update["earning"] = RetrofitClient.getRequestBodyFromString(earn)
        update["expenses"] =
            RetrofitClient.getRequestBodyFromString(expenses)
        update["workComment"] =
            RetrofitClient.getRequestBodyFromString(comment)
        update["dateOfCallSchedule"] = RetrofitClient.getRequestBodyFromString(dateCallSchedule)

        val partList: MutableList<MultipartBody.Part> = ArrayList()
        for (i in stringArrayListImage.indices) {
            try {
                val regDocumentPart = MultipartBody.Part.createFormData(
                    "otherDocuments[]",
                    stringArrayListImage[i].getFileName(),
                    RetrofitClient.getRequestBodyFromStringFile(
                        stringArrayListImage[i].getFilepath()!!
                    )
                )
                partList.add(regDocumentPart)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        RetrofitClient
            .getClient(Constant.ServerEndpoint.UPDATE_WORK_DET)
            .create(RetrofitInterfaces.UpdateWork::class.java)
            .post(update, partList)
            .enqueue(this)
    }

    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        try {
            var strResponse=response.body()!!.string()
            val jsonObject = JSONObject(strResponse)
            val result: JSONObject = jsonObject.getJSONObject("result")
            listener!!.onUpdateFetchSuccess(result.getString("msg"),result.getString("msg_string"))
        }catch(e:Exception)
        {
            e.printStackTrace()
        }
    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        TODO("Not yet implemented")
    }

}