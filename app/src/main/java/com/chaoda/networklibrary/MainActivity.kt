package com.chaoda.networklibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.chaoda.network.environment.NetworkEnvironmentActivity
import com.chaoda.network.response.*
import com.chaoda.networklibrary.model.Articles
import com.chaoda.networklibrary.retrofitutils.WanAndroidRetrofitUtils
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<TextView>(R.id.text)
        view.setOnClickListener {
            lifecycleScope.launch {
                val apiResponse = handleHttp {
                    WanAndroidRetrofitUtils.getService(IWxArticleChapters::class.java)
                        .getWxArticleChapters()
                }
                when (apiResponse) {
                    is SuccessfulApiResponse -> {
                        Log.e("ApiResponse:->->->", "onSuccess: ${apiResponse.data.toString()}")
                    }
                    is EmptyApiResponse -> {
                        Log.e("ApiResponse:->->->", "onEmpty")
                    }
                    is FailedApiResponse -> {
                        Log.e(
                            "ApiResponse:->->->",
                            "onFailed: code:${apiResponse.code},msg:${apiResponse.message}"
                        )
                    }
                    is ErrorApiResponse -> {
                        Log.e("ApiResponse:->->->", "onError: ${apiResponse.throwable.message}")
                    }
                }
            }
        }
        view.setOnLongClickListener {
            NetworkEnvironmentActivity.startEnvironmentActivity(this)
            true
        }
    }
}

interface IWxArticleChapters {
    @GET("wxarticle/chapters/json")
    suspend fun getWxArticleChapters(): ApiResponse<Articles>
}

interface IGithubEvents {
    @GET("events")
    fun getEvents(): Call<ResponseBody>
}