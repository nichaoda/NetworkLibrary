package com.chaoda.network.response

import com.google.gson.annotations.SerializedName

open class ApiResponse<T>(
    @SerializedName("errorCode") open val code: Int? = null,
    open val data: T? = null,
    @SerializedName("errorMsg") open val message: String? = null,
    open val throwable: Throwable? = null
) {
    /**
     * 业务方面请求结果,具体根据服务端的API文档修改
     */
    val isSuccessful: Boolean by lazy { code == 0 }
}

class EmptyApiResponse<T> : ApiResponse<T>()

data class SuccessfulApiResponse<T>(override val data: T) : ApiResponse<T>(data = data)

data class FailedApiResponse<T>(override val code: Int?, override val message: String?) :
    ApiResponse<T>(code, message = message)

data class ErrorApiResponse<T>(override val throwable: Throwable) :
    ApiResponse<T>(throwable = throwable)