package com.chaoda.networklibrary.application

import android.app.Application
import com.chaoda.network.RetrofitUtils
import com.chaoda.networklibrary.config.NetworkConfig

class NetworkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitUtils.initConfig(this, NetworkConfig())
    }
}