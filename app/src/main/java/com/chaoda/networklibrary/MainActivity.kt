package com.chaoda.networklibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.*
import com.chaoda.network.environment.NetworkEnvironmentActivity
import com.chaoda.network.response.*
import com.chaoda.network.support.NetworkLiveData
import com.chaoda.networklibrary.model.Articles
import com.chaoda.networklibrary.retrofitutils.WanAndroidRetrofitUtils
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val view = findViewById<TextView>(R.id.text)
        view.setOnClickListener {
            viewModel.getResponse()
        }
        viewModel.liveData.observeNetwork(this) {
            onSuccess {
                Log.e("Articles->->", it.toString())
            }

            onComplete {

            }
        }
        view.setOnLongClickListener {
            NetworkEnvironmentActivity.startEnvironmentActivity(this)
            true
        }
    }
}

class MainViewModel : ViewModel() {

    val liveData = NetworkLiveData<Articles>()

    fun getResponse() {
        viewModelScope.launch {
            liveData.value = handleHttp {
                WanAndroidRetrofitUtils.getService(IWxArticleChapters::class.java)
                    .getWxArticleChapters()
            }
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