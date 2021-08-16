package com.chaoda.networklibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.chaoda.network.environment.NetworkEnvironmentActivity
import com.chaoda.network.response.ApiResponse
import com.chaoda.networklibrary.model.Articles
import com.chaoda.networklibrary.retrofitutils.WanAndroidRetrofitUtils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<TextView>(R.id.text)
        view.setOnClickListener {
            WanAndroidRetrofitUtils.getService(IWxArticleChapters::class.java)
                .getWxArticleChapters().enqueue(object : Callback<ApiResponse<Articles>> {
                    override fun onResponse(
                        call: Call<ApiResponse<Articles>>,
                        response: Response<ApiResponse<Articles>>
                    ) {
                        Log.e("Response:->->->", "onResponse: " + response.body()?.data?.toString())
                    }

                    override fun onFailure(call: Call<ApiResponse<Articles>>, t: Throwable) {

                    }
                })
        }
        view.setOnLongClickListener {
            NetworkEnvironmentActivity.startEnvironmentActivity(this)
            true
        }
    }
}

interface IWxArticleChapters {
    @GET("wxarticle/chapters/json")
    fun getWxArticleChapters(): Call<ApiResponse<Articles>>
}

interface IGithubEvents {
    @GET("events")
    fun getEvents(): Call<ResponseBody>
}