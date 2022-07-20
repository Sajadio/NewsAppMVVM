
package com.example.newsappmvvm.data.network

import okhttp3.Interceptor
import okhttp3.Response

class HttpInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val requestBuilder = chain.request().newBuilder()

        return chain.proceed(requestBuilder.build())
    }

}