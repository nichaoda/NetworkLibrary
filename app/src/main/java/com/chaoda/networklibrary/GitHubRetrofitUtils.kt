package com.chaoda.networklibrary

import com.chaoda.network.AbsRetrofitUtils

object GitHubRetrofitUtils : AbsRetrofitUtils() {

    override fun getBaseUrl() = "https://api.github.com/"

}