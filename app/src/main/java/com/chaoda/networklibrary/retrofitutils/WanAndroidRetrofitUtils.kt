package com.chaoda.networklibrary.retrofitutils

import com.chaoda.network.RetrofitUtils
import com.chaoda.network.environment.NetworkEnvironmentActivity

object WanAndroidRetrofitUtils : RetrofitUtils() {

    val environment = NetworkEnvironmentActivity.getNetworkEnvironmentFromSP(applicationContext)

    val DEBUG = NetworkEnvironmentActivity.DEBUG_ENVIRONMENT

    override fun getBaseUrl() =
        if (environment == DEBUG) "https://www.wanandroid.com"
        else "https://release"

}