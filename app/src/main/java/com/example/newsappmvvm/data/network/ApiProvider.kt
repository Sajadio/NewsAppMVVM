package com.example.newsappmvvm.data.network

import android.util.Log
import com.example.newsappmvvm.utils.BASE_URL
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiProvider {


    private fun httpLoggingInterceptor(): HttpLoggingInterceptor{
        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("HttpLoggingInterceptor", "log: $message")
            }
        })
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }

    private val retrofit by lazy {

        val client = OkHttpClient.Builder()
        client.connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(httpLoggingInterceptor())
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

    val api by lazy {
        retrofit.create(ApiService::class.java)
    }
}