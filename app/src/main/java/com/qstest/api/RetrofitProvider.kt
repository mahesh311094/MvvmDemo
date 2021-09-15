package com.qstest.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    fun provideRetrofit(
        apiBaseUrl: String,
        okHttpClient: OkHttpClient,
        gson: Gson = GsonBuilder().create(),
    ): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(apiBaseUrl)
            addConverterFactory(GsonConverterFactory.create(gson))
            client(okHttpClient)
        }.build()
    }
}