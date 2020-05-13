package com.essensys.kotlinapplication.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

/**
 * Created by SM on 03/04/19.
 */

class RetrofitInterfaces {

    interface DoLogin {
        @POST("LOGIN")
        @FormUrlEncoded
        fun post(
            @Field("username") emailId: String,
            @Field("password") password: String
        ): Call<ResponseBody>
    }

    interface ForgotPassword {
        @POST("ForgotPassword")
        @FormUrlEncoded
        fun post(@Field("username") emailId: String): Call<ResponseBody>
    }

    interface GetDashboard {
        @POST("GetDashboard")
        @FormUrlEncoded
        fun post(
            @Header("USERAUTH") userAuth: String,
            @Field("company_id") company_id: String
        ): Call<ResponseBody>
    }

    interface TodaysWorkList {
        @POST("TodaysWorkList")
        @FormUrlEncoded
        fun post(
            @Field("user_id") user_id: String?,
            @Field("company_id") company_id: String?,
            @Field("user_type") user_type: String?,
            @Field("toDate") toDate: String?,
            @Field("fromDate") fromDate: String?,
            @Field("sr_city") sr_city: String?,
            @Field("sr_route") sr_route: String?,
            @Field("sr_category") sr_category: String?,
            @Field("sr_product") sr_product: String?,
            @Field("offset") offset: String?,
            @Field("work_status") work_status: String?
        ): Call<ResponseBody>
    }

    interface GetHomeData {
        @POST("GetHomeData")
        @FormUrlEncoded
        fun post(
            @Field("user_id") user_id: String?,
            @Field("company_id") company_id: String?,
            @Field("user_type") user_type: String?
        ): Call<ResponseBody>
    }

    interface GetFields {
        @POST("CityData")
        @FormUrlEncoded
        fun post(
            @Field("user_id") user_id: String,
            @Field("company_id") company_id: String,
            @Field("user_type") user_type: String,
            @Field("city_id") city_id: String,
            @Field("cat_id") cat_id: String
        ): Call<ResponseBody>
    }

    interface GetWorkDet {
        @POST("GetWorkDet")
        @FormUrlEncoded
        fun post(
            @Field("user_id") user_id: String,
            @Field("company_id") company_id: String,
            @Field("user_type") user_type: String,
            @Field("work_id") work_id: String
        ): Call<ResponseBody>
    }

    interface UpdateWork {
        @POST("UpdateWork")
        @Multipart
        fun post(
            @PartMap map: HashMap<String, RequestBody>,
            @Part filesArray: List<MultipartBody.Part>
        ): Call<ResponseBody>
    }
    interface UpdateAssignee {
        @POST("UpdateAssignee")
        @FormUrlEncoded
        fun post(
            @Field("user_id") user_id: String,
            @Field("company_id") company_id: String,
            @Field("user_type") user_type: String,
            @Field("work_id") work_id: String,
            @Field("assignedToUserId") assignedToUserId: String
        ): Call<ResponseBody>
    }
    interface GetCallList {
        @POST("GetCallList")
        @FormUrlEncoded
        fun post(
            @Field("user_id") user_id: String,
            @Field("company_id") company_id: String,
            @Field("user_type") user_type: String,
            @Field("offset") offset: String
        ): Call<ResponseBody>
    }

    interface CreateCall {
        @POST("CreateCall")
        @FormUrlEncoded
        fun post(
            @Field("user_id") user_id: String,
            @Field("company_id") company_id: String,
            @Field("user_type") user_type: String,
            @Field("customer_id") customer_id: String
        ): Call<ResponseBody>
    }

    interface EditCreateCall {
        @POST("EditCreateCall")
        @FormUrlEncoded
        fun post(
            @Field("user_id") user_id: String,
            @Field("company_id") company_id: String,
            @Field("user_type") user_type: String,
            @Field("customer_id") customer_id: String,
            @Field("type_service_sales") type_service_sales: String,
            @Field("cat_id") cat_id: String,
            @Field("productId") productId: String,
            @Field("dateOfSale") dateOfSale: String,
            @Field("serviceCriteria") serviceCriteria: String,
            @Field("serviceSalesRepeatFrequency") serviceSalesRepeatFrequency: String,
            @Field("numOfDaysRepeatFrequency") numOfDaysRepeatFrequency: String,
            @Field("dateOfRepeatFrequency") dateOfRepeatFrequency: String,
            @Field("dateOfServieFrom") dateOfServieFrom: String,
            @Field("dateOfServieTo") dateOfServieTo: String,
            @Field("workComment") workComment: String,
            @Field("singleServiceDate") singleServiceDate: String

        ): Call<ResponseBody>
    }

    interface AddNewCall {
        @POST("AddNewCall")
        @Multipart
        fun post(
            @PartMap map: HashMap<String, RequestBody>,
            @Part filesArray: List<MultipartBody.Part>
        ): Call<ResponseBody>
    }

    interface GetCustList {
        @POST("GetCustList")
        @FormUrlEncoded
        fun post(
            @Field("user_id") user_id: String,
            @Field("company_id") company_id: String,
            @Field("user_type") user_type: String,
            @Field("sr_city") sr_city: String,
            @Field("sr_route ") sr_route: String,
            @Field("offset") offset: String
        ): Call<ResponseBody>
    }

    interface AddNewCust {
        @POST("AddNewCust")
        @Multipart
        fun post(
            @PartMap map: HashMap<String, RequestBody>,
            @Part filesArray: List<MultipartBody.Part>
        ): Call<ResponseBody>
    }

    interface UpdateNewCust {
        @POST("UpdateNewCust")
        @Multipart
        fun post(
            @PartMap map: HashMap<String, RequestBody>,
            @Part filesArray: List<MultipartBody.Part>
        ): Call<ResponseBody>
    }

    interface GetCustDet {
        @POST("GetCustDet")
        @FormUrlEncoded
        fun post(
            @Field("user_id") user_id: String,
            @Field("company_id") company_id: String,
            @Field("user_type") user_type: String,
            @Field("customer_id") customer_id: String
        ): Call<ResponseBody>
    }
}



