package com.Homework4ZhengZhu.hw4.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BitcoinsAPI {
    @GET("bitcoins")
    suspend fun fetchCats(
        @Query("tag") tag: String,
    ): Response<List<Bitcoin>>
}