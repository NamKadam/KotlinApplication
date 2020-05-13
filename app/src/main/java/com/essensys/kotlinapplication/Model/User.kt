package com.essensys.kotlinapplication.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User
{
    @SerializedName("msg")
    @Expose
    private var msg: String? = null

    @SerializedName("msg_string")
    @Expose
    private var msgString: String? = null
    @SerializedName("company_id")
    @Expose

    private var companyId: String? = null
    @SerializedName("account_id")
    @Expose
    private var accountId: String? = null
    @SerializedName("user_id")
    @Expose
    private var userId: String? = null
    @SerializedName("user_name")
    @Expose
    private var userName: String? = null
    @SerializedName("user_type")
    @Expose
    private var userType: String? = null
    @SerializedName("user_email_id")
    @Expose
    private var userEmailId: String? = null
    @SerializedName("profile_photo")
    @Expose
    private var profilePhoto: String? = null
    @SerializedName("profile_allowToCreateCalls")
    @Expose
    private var profileAllowToCreateCalls: String? = null
    @SerializedName("profile_allowToCreateUser")
    @Expose
    private var profileAllowToCreateUser: String? = null
    @SerializedName("profile_allowToCreateCustomer")
    @Expose
    private var profileAllowToCreateCustomer: String? = null

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

    fun getCompanyId(): String? {
        return companyId
    }

    fun setCompanyId(companyId: String) {
        this.companyId = companyId
    }

    fun getAccountId(): String? {
        return accountId
    }

    fun setAccountId(accountId: String) {
        this.accountId = accountId
    }

    fun getUserId(): String? {
        return userId
    }

    fun setUserId(userId: String) {
        this.userId = userId
    }

    fun getUserName(): String? {
        return userName
    }

    fun setUserName(userName: String) {
        this.userName = userName
    }

    fun getUserType(): String? {
        return userType
    }

    fun setUserType(userType: String) {
        this.userType = userType
    }

    fun getUserEmailId(): String? {
        return userEmailId
    }

    fun setUserEmailId(userEmailId: String) {
        this.userEmailId = userEmailId
    }

    fun getProfilePhoto(): String? {
        return profilePhoto
    }

    fun setProfilePhoto(profilePhoto: String) {
        this.profilePhoto = profilePhoto
    }

    fun getProfileAllowToCreateCalls(): String? {
        return profileAllowToCreateCalls
    }

    fun setProfileAllowToCreateCalls(profileAllowToCreateCalls: String) {
        this.profileAllowToCreateCalls = profileAllowToCreateCalls
    }

    fun getProfileAllowToCreateUser(): String? {
        return profileAllowToCreateUser
    }

    fun setProfileAllowToCreateUser(profileAllowToCreateUser: String) {
        this.profileAllowToCreateUser = profileAllowToCreateUser
    }

    fun getProfileAllowToCreateCustomer(): String? {
        return profileAllowToCreateCustomer
    }

    fun setProfileAllowToCreateCustomer(profileAllowToCreateCustomer: String) {
        this.profileAllowToCreateCustomer = profileAllowToCreateCustomer
    }

}

