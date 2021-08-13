package com.chaoda.network

import com.chaoda.network.config.INetworkConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class RetrofitUtils {

    companion object {
        private var networkConfig: INetworkConfig? = null

        fun initConfig(config: INetworkConfig) {
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
        if (!this::mOkHttpClient.isInitialized) {
            val builder = OkHttpClient.Builder()
            networkConfig?.apply {
                if (isDebug()) {
                    builder.addInterceptor(
                        HttpLoggingInterceptor().setLevel(
                            HttpLoggingInterceptor.Level.BODY
                        )
                    )
                }
            }
            mOkHttpClient = builder.build()
        }
        return mOkHttpClient
    }

    fun <T> getService(service: Class<T>): T {
        return getRetrofit(getBaseUrl()).create(service)
    }
}