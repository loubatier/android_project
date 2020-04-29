package com.example.network.services

import com.example.network.models.ArticleResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


    interface ArticleService {
        @GET("/v2/top-headlines")
        fun list(@Query("category") query: String): Call<ArticleResult>
        @GET("/v2/top-headlines")
        fun sourceList(@Query("source") query: String): Call<ArticleResult>
        @GET("/v2/top-headlines")
        fun countryList(@Query("country") query: String): Call<ArticleResult>
    }
