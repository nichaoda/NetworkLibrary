package com.chaoda.network

import android.content.Context
import com.chaoda.network.config.INetworkConfig
import com.chaoda.network.interceptor.CommonRequestInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class RetrofitUtils {

    companion object {
        var applicationContext: Context? = null
        private var networkConfig: INetworkConfig? = null

        fun initConfig(context: Context, config: INetworkConfig? = null) {
            applicationContext = context
            networkConfig = config
        }
    }

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
        if (applicationContext == null)
            throw Exception("Please use RetrofitUtils.initConfig at Application first.")
        if (!this::mOkHttpClient.isInitialized) {
            val builder = OkHttpClient.Builder()
            if (networkConfig?.isDebug() == true) {
                builder.addInterceptor(
                    HttpLoggingInterceptor().setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    )
                )
            }
            getInterceptor()?.apply {
                builder.addInterceptor(this)
            }
            builder.addInterceptor(CommonRequestInterceptor(networkConfig))
            mOkHttpClient = builder.build()
        }
        return mOkHttpClient
    }

    fun <T> getService(service: Class<T>): T {
        return getRetrofit(getBaseUrl()).create(service)
    }

    /**
     * 根据需求在子类中自定义需要的拦截器
     */
    open fun getInterceptor(): Interceptor? {
        return null
    }
}