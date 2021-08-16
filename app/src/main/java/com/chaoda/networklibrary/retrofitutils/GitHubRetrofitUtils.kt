package com.chaoda.networklibrary.retrofitutils

import com.chaoda.network.RetrofitUtils

object GitHubRetrofitUtils : RetrofitUtils() {

    override fun getBaseUrl() = "https://api.github.com/"

}