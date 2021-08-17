package com.chaoda.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 添加公共Header,需要根据实际项目配置
 */
class CommonRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}