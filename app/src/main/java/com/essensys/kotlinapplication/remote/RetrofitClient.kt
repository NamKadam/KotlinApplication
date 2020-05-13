package com.essensys.kotlinapplication.remote

import com.google.gson.GsonBuilder

import java.io.File

import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var retrofit: Retrofit? = null
    private var retrofithome: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        val gson = GsonBuilder().setLenient().create()

        retrofit = Retrofit.Builder()
            .baseUrl("http://52.41.183.204/JB086/companyservice/web-app/" + baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))

            .build()

        return retrofit as Retrofit
    }

    fun getRequestBodyFromString(str: String?): RequestBody {
        return RequestBody.create(MediaType.parse("multipart/form-data"), str)
    }

    fun getRequestBodyFromStringFile(str: String): RequestBody {
        val file = File(str)
        return RequestBody.create(MediaType.parse("*/*"), file)
    }

    fun getRequestBodyFromFile(file: File): RequestBody {
        return RequestBody.create(MediaType.parse("*/*"), file)
    }

    fun getClientHome(baseUrl: String): Retrofit? {
        val gson = GsonBuilder().setLenient().create()

        if (retrofithome == null) {
            retrofithome = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofithome
    }
}