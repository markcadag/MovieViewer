package com.markcadag.movieviewer.service

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by markcadag on 4/7/18.
 */
object ServiceGenerator {

    private val PROD_URL = "http://ec2-52-76-75-52.ap-southeast-1.compute.amazonaws.com/"


    private var gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .serializeNulls()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()

    private val builder = Retrofit.Builder()
            .baseUrl(PROD_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // <- add this
            .addConverterFactory(GsonConverterFactory.create(gson))

    var retrofit = builder.build()

    private val logging = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClient = OkHttpClient.Builder()

    /**
     * Create service that needs authorization
     */
    fun <S> createService(
            serviceClass: Class<S>): S {

        addLoggingInterceptor()
        return retrofit.create(serviceClass)
    }

    /**
     * Add logging interceptor, for debugging purposes
     */
    private fun addLoggingInterceptor() {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging)
            builder.client(httpClient.build())
            retrofit = builder.build()
        }
    }
}