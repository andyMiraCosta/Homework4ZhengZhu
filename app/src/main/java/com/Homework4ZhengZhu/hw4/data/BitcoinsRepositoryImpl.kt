package com.Homework4ZhengZhu.hw4.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class BitcoinsRepositoryImpl(
    private  val bitcoinsAPI: BitcoinsAPI,
    private val dispatcher: CoroutineDispatcher
): BitcoinsRepository {
    override suspend fun getBitcoins(): NetworkResult<List<Bitcoin>> {
        return withContext(dispatcher) {
            try {
                val response = bitcoinsAPI.fetchCats("cute")
                if (response.isSuccessful) {
                    NetworkResult.Success(response.body()!!)
                } else {
                    NetworkResult.Error(response.errorBody().toString())
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}