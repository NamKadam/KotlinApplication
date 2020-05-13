package com.essensys.kotlinapplication.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductData {
    @SerializedName("product_id")
    @Expose
    private var productId: String? = null

    @SerializedName("product_name")
    @Expose
    private var productName: String? = null

    @SerializedName("category_id")
    @Expose
    private var categoryId: String? = null

    /**
     * Pay attention here, you have to override the toString method as the
     * ArrayAdapter will reads the toString of the given object for the name
     *
     * @return contact_name
     */
    override fun toString(): String {
        return productName!!
    }

    fun getProductId(): String? {
        return productId
    }

    fun setProductId(productId: String?) {
        this.productId = productId
    }

    fun getProductName(): String? {
        return productName
    }

    fun setProductName(productName: String?) {
        this.productName = productName
    }

    fun getCategoryId(): String? {
        return categoryId
    }

    fun setCategoryId(categoryId: String?) {
        this.categoryId = categoryId
    }
}