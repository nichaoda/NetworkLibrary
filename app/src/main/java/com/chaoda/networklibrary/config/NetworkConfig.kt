package com.chaoda.networklibrary.config

import com.chaoda.network.config.INetworkConfig
import com.chaoda.networklibrary.BuildConfig

class NetworkConfig : INetworkConfig {
    override fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    override fun getAppVersionName(): String {
        return BuildConfig.VERSION_NAME
    }

    override fun getAppVersionCode(): String {
        return BuildConfig.VERSION_CODE.toString()
    }
}