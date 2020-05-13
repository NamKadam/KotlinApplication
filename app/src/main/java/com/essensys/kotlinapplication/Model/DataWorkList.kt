package com.essensys.kotlinapplication.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataWorkList {
    @SerializedName("customer_id")
    @Expose
    var customerId: String? = null

    @SerializedName("company_id")
    @Expose
    var companyId: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("city")
    @Expose
    var city: String? = null

    @SerializedName("route")
    @Expose
    var route: String? = null

    @SerializedName("mobile")
    @Expose
    var mobile: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("gstNumber")
    @Expose
    var gstNumber: String? = null

    @SerializedName("panNo")
    @Expose
    var panNo: String? = null

    @SerializedName("cinNo")
    @Expose
    var cinNo: String? = null

    @SerializedName("type_of_customer")
    @Expose
    var typeOfCustomer: String? = null

    @SerializedName("customerAddress")
    @Expose
    var customerAddress: String? = null

    @SerializedName("customer_status")
    @Expose
    var customerStatus: String? = null

    @SerializedName("on_datetime")
    @Expose
    var onDatetime: String? = null

    @SerializedName("createdByUserId")
    @Expose
    var createdByUserId: String? = null

    @SerializedName("locationGoogle")
    @Expose
    var locationGoogle: String? = null

    @SerializedName("userLocationGoogle")
    @Expose
    var userLocationGoogle: String? = null

    @SerializedName("work_id")
    @Expose
    var workId: String? = null

    @SerializedName("type_service_sales")
    @Expose
    var typeServiceSales: String? = null

    @SerializedName("serviceCriteria")
    @Expose
    var serviceCriteria: String? = null

    @SerializedName("call_schedule_ondate")
    @Expose
    var callScheduleOndate: String? = null

    @SerializedName("comment")
    @Expose
    var comment: String? = null

    @SerializedName("product_category_id")
    @Expose
    var productCategoryId: String? = null

    @SerializedName("product_id")
    @Expose
    var productId: String? = null

    @SerializedName("city_id")
    @Expose
    var cityId: String? = null

    @SerializedName("route_id")
    @Expose
    var routeId: String? = null

    @SerializedName("work_status")
    @Expose
    var workStatus: String? = null

    @SerializedName("assignedToUserId")
    @Expose
    var assignedToUserId: String? = null

    @SerializedName("earning")
    @Expose
    var earning: String? = null

    @SerializedName("expenses")
    @Expose
    var expenses: String? = null

    @SerializedName("product_name")
    @Expose
    var productName: String? = null

    @SerializedName("cat_name")
    @Expose
    var catName: String? = null

    @SerializedName("createdBy")
    @Expose
    var createdBy: String? = null

    @SerializedName("admin_type")
    @Expose
    var adminType: String? = null

    @SerializedName("assignToNameAdminType")
    @Expose
    var assignToNameAdminType: String? = null

    @SerializedName("assignToName")
    @Expose
    var assignToName: String? = null

    @SerializedName("city_name")
    @Expose
    var cityName: String? = null

    @SerializedName("route_name")
    @Expose
    var routeName: String? = null

}
