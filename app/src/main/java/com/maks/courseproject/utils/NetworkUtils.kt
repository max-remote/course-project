package com.maks.courseproject.utils

import com.maks.courseproject.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://rickandmortyapi.com/api/"
const val BASE_PAGE = 1

fun Retrofit.Builder.setClient() = apply {
    val okHttpClient = OkHttpClient.Builder()
        .addHeaderInterceptor()
        .addHttpLoggingInterceptor()
        .build()

    this.client(okHttpClient)
}

private fun OkHttpClient.Builder.addHeaderInterceptor() = apply {
    val interceptor = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .build()

        chain.proceed(request)
    }

    this.addInterceptor(interceptor)
}

private fun OkHttpClient.Builder.addHttpLoggingInterceptor() = apply {
    if (BuildConfig.DEBUG) {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        this.addNetworkInterceptor(interceptor)
    }
}

fun Retrofit.Builder.addJsonConverter() = apply {
    val gsonFactory = GsonConverterFactory.create()
    this.addConverterFactory(gsonFactory)
}