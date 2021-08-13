package com.chaoda.networklibrary

import com.chaoda.network.RetrofitUtils

object GitHubRetrofitUtils : RetrofitUtils() {

    override fun getBaseUrl() = "https://api.github.com/"

}