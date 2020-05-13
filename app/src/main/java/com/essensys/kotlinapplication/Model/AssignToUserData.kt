package com.essensys.kotlinapplication.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AssignToUserData {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("admin_type")
    @Expose
    private var adminType: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("email")
    @Expose
    private var email: String? = null

    /**
     * Pay attention here, you have to override the toString method as the
     * ArrayAdapter will reads the toString of the given object for the name
     *
     * @return contact_name
     */
    override fun toString(): String {
        return name!!
    }
    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getAdminType(): String? {
        return adminType
    }

    fun setAdminType(adminType: String?) {
        this.adminType = adminType
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }

}