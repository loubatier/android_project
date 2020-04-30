package com.example.network.services

import com.example.network.models.SourceResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SourceService {
    @GET("/v2/sources")
    fun list(): Call<SourceResult>
}