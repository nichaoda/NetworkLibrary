package com.chaoda.network.application

import android.app.Application

open class NetworkApplication : Application() {

    companion object {
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}