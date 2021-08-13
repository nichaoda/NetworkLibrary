package com.chaoda.network.interceptor

import com.chaoda.network.config.INetworkConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 添加公共Header,需要根据实际项目配置
 */
class CommonRequestInterceptor(private val networkConfig: INetworkConfig?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (networkConfig != null) {
            builder.addHeader("os", "android")
            builder.addHeader("version_code", networkConfig.getAppVersionCode())
            builder.addHeader("version_name", networkConfig.getAppVersionName())
        }
        return chain.proceed(builder.build())
    }
}