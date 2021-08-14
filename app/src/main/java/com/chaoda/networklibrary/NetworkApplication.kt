package com.chaoda.networklibrary

import android.app.Application
import com.chaoda.network.RetrofitUtils

class NetworkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitUtils.initConfig(this, NetworkConfig())
    }
}