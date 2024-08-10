package com.kangengineering.chapterseven.data

interface PetsRepository {
    suspend fun getPets(): NetworkResult<List<Cat>>
}