package com.chaoda.network.support

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.chaoda.network.response.ApiResponse

class NetworkLiveData<T> : MutableLiveData<ApiResponse<T>>() {

    /**
     * [block] 外部可以调用Listeners<T>中的函数
     */
    fun observeNetwork(owner: LifecycleOwner, block: Listeners<T>.() -> Unit) {
        // apply(block)是为了将外部调用的onSuccess、onFailed、onError等方法与该listeners关联起来
        val listeners = Listeners<T>().apply(block)
        val observer = object : NetworkObserver<T>() {
            override fun onSuccess(data: T) {
                listeners.successfulListener?.invoke(data)
            }

            override fun onEmpty() {
                listeners.emptyListener?.invoke()
            }

            override fun onFailed(code: Int?, message: String?) {
                listeners.failedListener?.invoke(code, message)
            }

            override fun onError(throwable: Throwable) {
                listeners.errorListener?.invoke(throwable)
            }

            override fun onComplete() {
                listeners.completeListener?.invoke()
            }
        }
        super.observe(owner, observer)
    }
}