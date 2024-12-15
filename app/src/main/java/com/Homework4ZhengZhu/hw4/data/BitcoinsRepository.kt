package com.Homework4ZhengZhu.hw4.data

interface BitcoinsRepository {
    suspend fun getBitcoins(): NetworkResult<List<Bitcoin>>
}