package com.example.network.repositories

import android.os.Build
import com.example.network.models.Article
import com.example.network.models.Source
import com.example.network.services.SourceService
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class Sourcepository {
    private val service: SourceService
    private val requestInterceptor: Interceptor = Interceptor { chain ->
        val original: Request = chain.request()

        val url: HttpUrl = original.url
            .newBuilder()
            .addQueryParameter("apiKey", "200766c9dedb4223acce693bfcaf5941")
            .build()

        val device = Build.MANUFACTURER + "-" + Build.MODEL
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


        service = retrofit.create(SourceService::class.java)
    }

    fun list(): List<Source> {
        val response = service.list().execute()
        return response.body()?.sources ?: emptyList()
    }
}