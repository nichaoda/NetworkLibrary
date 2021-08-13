package com.chaoda.network.config

/**
 * 网络请求的相关配置信息,以及拦截器中可能使用的公共参数
 */
interface INetworkConfig {

    fun isDebug(): Boolean

    fun getAppVersionName(): String

    fun getAppVersionCode(): String
}