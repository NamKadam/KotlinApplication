package com.essensys.kotlinapplication.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CityData {
    @SerializedName("city_id")
    @Expose
    private var cityId: String? = null

    @SerializedName("city_name")
    @Expose
    private var cityName: String? = null


    fun getCityId(): String? {
        return cityId
    }

    /**
     * Pay attention here, you have to override the toString method as the
     * ArrayAdapter will reads the toString of the given object for the name
     *
     * @return contact_name
     */
    override fun toString(): String {
        return cityName!!
    }

    fun setCityId(cityId: String?) {
        this.cityId = cityId
    }

    fun getCityName(): String? {
        return cityName
    }

    fun setCityName(cityName: String?) {
        this.cityName = cityName
    }
}