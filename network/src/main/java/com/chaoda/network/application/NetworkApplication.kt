package com.chaoda.network.application

import android.app.Application
import com.chaoda.network.RetrofitUtils

open class NetworkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitUtils.initConfig(this)
    }
}