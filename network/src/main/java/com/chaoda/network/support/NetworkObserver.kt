package com.chaoda.network.support

import androidx.lifecycle.Observer
import com.chaoda.network.response.*

abstract class NetworkObserver<T> : Observer<ApiResponse<T>> {

    override fun onChanged(apiResponse: ApiResponse<T>) {
        when (apiResponse) {
            is SuccessfulApiResponse -> onSuccess(apiResponse.data)
            is EmptyApiResponse -> onEmpty()
            is FailedApiResponse -> onFailed(apiResponse.code, apiResponse.message)
            is ErrorApiResponse -> onError(apiResponse.throwable)
        }
        onComplete()
    }

    abstract fun onSuccess(data: T)

    abstract fun onEmpty()

    abstract fun onFailed(code: Int?, message: String?)

    abstract fun onError(throwable: Throwable)

    abstract fun onComplete()
}