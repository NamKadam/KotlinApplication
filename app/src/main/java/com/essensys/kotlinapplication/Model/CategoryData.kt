package com.essensys.kotlinapplication.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CategoryData {
    @SerializedName("cat_id")
    @Expose
    private var catId: String? = null

    @SerializedName("cat_name")
    @Expose
    private var catName: String? = null

    /**
     * Pay attention here, you have to override the toString method as the
     * ArrayAdapter will reads the toString of the given object for the name
     *
     * @return contact_name
     */
    override fun toString(): String {
        return catName!!
    }

    fun getCatId(): String? {
        return catId
    }

    fun setCatId(catId: String?) {
        this.catId = catId
    }

    fun getCatName(): String? {
        return catName
    }

    fun setCatName(catName: String?) {
        this.catName = catName
    }
}