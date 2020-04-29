package com.example.network.repositories

import android.os.Build
import android.util.Config.DEBUG
import com.example.network.models.Article
import com.example.network.services.ArticleService
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class Articlepository {
    private val service: ArticleService
    private val requestInterceptor: Interceptor = Interceptor { chain ->
        val original: Request = chain.request()

        val url: HttpUrl = original.url
            .newBuilder()
            .addQueryParameter("apiKey", "200766c9dedb4223acce693bfcaf5941")
            .build()

        val device = Build.MANUFACTURER + "-" + Build.MODEL
        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
            .addHeader("User-Agent", "Android-($device)")
            .addHeader("Accept-Language", Locale.getDefault().language)
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .url(url)

        val request = requestBuilder.build()
        return@Interceptor chain.proceed(request)
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (true) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
        .addInterceptor(requestInterceptor)
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()
    init {




        val retrofit = Retrofit.Builder().apply {
            baseUrl("https://newsapi.org")
        }.addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()


        service = retrofit.create(ArticleService::class.java)
    }
    fun list(query:String): List<Article> {
        val response = service.list(query).execute()
        return response.body()?.articles ?: emptyList()
    }
    fun countryList(query:String): List<Article> {
        val response = service.countryList(query).execute()
        return response.body()?.articles ?: emptyList()
    }
    fun sourceList(query:String): List<Article> {
        val response = service.sourceList(query).execute()
        return response.body()?.articles ?: emptyList()
    }
}