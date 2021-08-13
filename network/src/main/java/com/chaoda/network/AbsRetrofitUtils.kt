package com.chaoda.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class AbsRetrofitUtils {

    private lateinit var mOkHttpClient: OkHttpClient

    /**
     * 一个应用中可能存在多个不同域名的网络请求,根据域名将对应的Retrofit缓存
     */
    private var retrofitHashMap = mutableMapOf<String, Retrofit>()

    abstract fun getBaseUrl(): String

    private fun getRetrofit(url: String): Retrofit {
        return retrofitHashMap[url] ?: run {
            val retrofit = Retrofit.Builder().baseUrl(url)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofitHashMap[url] = retrofit
            retrofit
        }

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
        return getRetrofit(getBaseUrl()).create(service)
    }
}