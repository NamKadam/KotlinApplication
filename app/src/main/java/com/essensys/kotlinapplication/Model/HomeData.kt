package com.essensys.kotlinapplication.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HomeData{
    @SerializedName("msg")
    @Expose
    private var msg: String? = null

    @SerializedName("msg_string")
    @Expose
    private var msgString: String? = null
    @SerializedName("company_logo")
    @Expose
    private var company_logo: String? = null

    @SerializedName("company_name")
    @Expose
    private var company_name: String? = null

    @SerializedName("todayWorkCount")
    @Expose
    private var todayWorkCount: String? = null

    @SerializedName("closedWorkCount")
    @Expose
    private var closedWorkCount: String? = null

    @SerializedName("rescheduleWorkCount")
    @Expose
    private var rescheduleWorkCount: String? = null

    @SerializedName("appUrl")
    @Expose
    private var appUrl: String? = null
    @SerializedName("appVersionCode")
    @Expose
    private var appVersionCode: String? = null

    fun getMsg(): String? {
        return msg
    }

    fun setMsg(msg: String) {
        this.msg = msg
    }
    fun getMsgString(): String? {
        return msgString
    }

    fun setMsgString(msgString: String) {
        this.msgString = msgString
    }

    fun getCompanyLogo(): String? {
        return company_logo
    }

    fun setCompanyLogo(company_logo: String) {
        this.company_logo = company_logo
    }

    fun getCompanyName(): String? {
        return company_name
    }

    fun setCompanyName(company_name: String) {
        this.company_name = company_name
    }

    fun getTodayWorkCount(): String? {
        return todayWorkCount
    }

    fun setTodayWorkCount(todayWorkCount: String) {
        this.todayWorkCount = todayWorkCount
    }

    fun getRescheduleWorkCount(): String? {
        return rescheduleWorkCount
    }

    fun setRescheduleWorkCount(rescheduleWorkCount: String) {
        this.rescheduleWorkCount = rescheduleWorkCount
    }

    fun getClosedWorkCount(): String? {
        return closedWorkCount
    }

    fun setClosedWorkCount(closedWorkCount: String) {
        this.closedWorkCount = closedWorkCount
    }

    fun getAppUrl():String?
    {
        return appUrl
    }
    fun setAppUrl(appUrl:String){
        this.appUrl=appUrl
    }

    fun getAppVersionCode():String?{
        return appVersionCode
    }
    fun setAppVersionCode(appVersionCode:String){
        this.appVersionCode=appVersionCode
    }

}