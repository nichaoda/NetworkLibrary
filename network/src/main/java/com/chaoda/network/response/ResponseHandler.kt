package com.chaoda.network.response

import android.util.Log
import com.chaoda.network.BuildConfig

/**
 * 处理Http请求
 */
suspend fun <T> handleHttp(block: suspend () -> ApiResponse<T>): ApiResponse<T> {
    runCatching {
        block.invoke()
    }.onSuccess { apiResponse: ApiResponse<T> ->
        return handleHttpSuccess(apiResponse)
    }.onFailure { throwable: Throwable ->
        if (BuildConfig.DEBUG) Log.e("handleHttp", "Error:", throwable)
        return ErrorApiResponse(throwable)
    }
    return EmptyApiResponse()
}

/**
 * Http请求处理成功了,业务方面需要进一步判断
 */
private fun <T> handleHttpSuccess(apiResponse: ApiResponse<T>): ApiResponse<T> {
    return if (apiResponse.isSuccessful) {
        // 业务处理成功,获取具体的值
        getSuccessfulResult(apiResponse)
    } else {
        // 业务处理没成功
        if (BuildConfig.DEBUG)
            Log.e(
                "handleHttp",
                "Failed: code:${apiResponse.code}    message:${apiResponse.message}"
            )
        FailedApiResponse(apiResponse.code, apiResponse.message)
    }
}

/**
 * 业务处理成功,获取具体的值
 */
private fun <T> getSuccessfulResult(apiResponse: ApiResponse<T>): ApiResponse<T> {
    val data = apiResponse.data
    // 返回的内容如果为null 或者 返回的内容是List并且List.size==0
    return if ((data == null) || (data is List<*> && data.isEmpty())) {
        EmptyApiResponse()
    } else {
        SuccessfulApiResponse(data)
    }
}
