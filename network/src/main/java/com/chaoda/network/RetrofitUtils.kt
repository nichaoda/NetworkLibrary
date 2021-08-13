package com.chaoda.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtils {

    private lateinit var mOkHttpClient: OkHttpClient
    private lateinit var mRetrofit: Retrofit

    private fun getRetrofit(): Retrofit {
        if (!this::mRetrofit.isInitialized) {
            val builder = Retrofit.Builder()
            mRetrofit = builder.baseUrl("https://www.wanandroid.com/")
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return mRetrofit
    }

    private fun getOkHttpClient(): OkHttpClient {
        if (!this::mOkHttpClient.isInitialized) {
            mOkHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        }
        return mOkHttpClient
    }

    fun <T> getService(service: Class<T>): T {
        return getRetrofit().create(service)
    }
}