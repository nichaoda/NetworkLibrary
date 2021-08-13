package com.chaoda.networklibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
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
                .getWxArticleChapters().enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Log.e("Response:->->->", "onResponse: " + response.body())
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    }
                })
        }
        view.setOnLongClickListener {
            GitHubRetrofitUtils.getService(IGithubEvents::class.java)
                .getEvents().enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Log.e("Response:->->->", "onResponse: " + response.body())
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    }
                })
            true
        }
    }
}

interface IWxArticleChapters {
    @GET("wxarticle/chapters/json")
    fun getWxArticleChapters(): Call<ResponseBody>
}

interface IGithubEvents {
    @GET("events")
    fun getEvents(): Call<ResponseBody>
}