package com.essensys.kotlinapplication.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RouteData {
    @SerializedName("route_id")
    @Expose
    private var routeId: String? = null

    @SerializedName("route_name")
    @Expose
    private var routeName: String? = null

    @SerializedName("city_id")
    @Expose
    private var cityId: String? = null

    /**
     * Pay attention here, you have to override the toString method as the
     * ArrayAdapter will reads the toString of the given object for the name
     *
     * @return contact_name
     */
    override fun toString(): String {
        return routeName!!
    }

    fun getRouteId(): String? {
        return routeId
    }

    fun setRouteId(routeId: String?) {
        this.routeId = routeId
    }

    fun getRouteName(): String? {
        return routeName
    }

    fun setRouteName(routeName: String?) {
        this.routeName = routeName
    }

    fun getCityId(): String? {
        return cityId
    }

    fun setCityId(cityId: String?) {
        this.cityId = cityId
    }
}