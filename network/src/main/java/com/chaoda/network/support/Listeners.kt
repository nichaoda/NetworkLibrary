package com.chaoda.network.support

class Listeners<T> {
    var successfulListener: ((T) -> Unit)? = null
    var emptyListener: (() -> Unit)? = null
    var failedListener: ((Int?, String?) -> Unit)? = null
    var errorListener: ((Throwable) -> Unit)? = null
    var completeListener: (() -> Unit)? = null

    fun onSuccess(block: (T) -> Unit) {
        successfulListener = block
    }

    fun onEmpty(block: () -> Unit) {
        emptyListener = block
    }

    fun onFailed(block: (Int?, String?) -> Unit) {
        failedListener = block
    }

    fun onError(block: (Throwable) -> Unit) {
        errorListener = block
    }

    fun onComplete(block: () -> Unit) {
        completeListener = block
    }
}