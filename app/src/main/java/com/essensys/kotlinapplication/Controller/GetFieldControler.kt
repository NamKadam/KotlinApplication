package com.essensys.kotlinapplication.Controller

import android.content.Context
import com.essensys.kotlinapplication.Model.CategoryData
import com.essensys.kotlinapplication.Model.CityData
import com.essensys.kotlinapplication.Model.ProductData
import com.essensys.kotlinapplication.Model.RouteData
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

class GetFieldControler : Callback<ResponseBody> {
     var mContext: Context? = null
     var listener: GetFieldListener? = null

    constructor(
        mContext: Context?,
        listener: GetFieldListener?
    ) {
        this.mContext = mContext
        this.listener = listener
    }

    interface GetFieldListener {
        fun onListFetchSuccess(
            mArraylistCityTemp: ArrayList<CityData>?,
            mArraylistRouteTemp: ArrayList<RouteData>?,
            mArraylistProduct: ArrayList<ProductData>?,
            mArraylistCat: ArrayList<CategoryData>?
        )

        fun onListFetchFailure(errorMsg: String?)
    }

    fun fetchGetFieldsManager(city_id: String, cat_id: String) {
        val user_id: String
        val company_id: String
        val user_type: String
        user_id = App.getCurrentUser(mContext)!!.getUserId().toString()
        company_id = App.getCurrentUser(mContext)!!.getCompanyId().toString()
        user_type = App.getCurrentUser(mContext)!!.getUserType().toString()
        RetrofitClient
            .getClient(Constant.ServerEndpoint.GET_FIELDS)
            .create(RetrofitInterfaces.GetFields::class.java)
            .post(user_id, company_id, user_type, city_id, cat_id)
            .enqueue(this)
    }
    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
           try {
               var strResponse=response.body()!!.string()
               val jsonObject = JSONObject(strResponse)
               val result=jsonObject.getJSONObject("result")
               if(result.getString("msg").equals("1"))
               {
                   val jsonCompany=result.getJSONArray("companyCityList")
                   val jsonRoute = result.getJSONArray("companyRouteList")
                   val jsonCategory = result.getJSONArray("companyCategoryList")
                   val jsonProduct = result.getJSONArray("companyProductList")

                   val mArraylistCityTemp: ArrayList<CityData> =
                       Gson().fromJson<ArrayList<CityData>>(
                           jsonCompany.toString(),
                           object : TypeToken<ArrayList<CityData?>?>() {}.type
                       )
                   val mArraylistRouteTemp =
                       Gson().fromJson<ArrayList<RouteData>>(
                           jsonRoute.toString(),
                           object : TypeToken<ArrayList<RouteData?>?>() {}.type
                       )
                   val mArraylistProdTemp =
                       Gson().fromJson<ArrayList<ProductData>>(
                           jsonProduct.toString(),
                           object : TypeToken<ArrayList<ProductData?>?>() {}.type
                       )

                   val mArraylistCatTemp =
                       Gson().fromJson<ArrayList<CategoryData>>(
                           jsonCategory.toString(),
                           object : TypeToken<ArrayList<CategoryData?>?>() {}.type
                       )
                   listener!!.onListFetchSuccess(mArraylistCityTemp,mArraylistRouteTemp,mArraylistProdTemp,mArraylistCatTemp)
               }
           }catch (e:Exception)
           {
               e.printStackTrace()
           }
    }
    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        TODO("Not yet implemented")
    }



}