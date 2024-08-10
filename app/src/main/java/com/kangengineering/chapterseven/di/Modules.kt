package com.kangengineering.chapterseven.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kangengineering.chapterseven.data.CatsAPI
import com.kangengineering.chapterseven.data.PetsRepository
import com.kangengineering.chapterseven.data.PetsRepositoryImpl
import com.kangengineering.chapterseven.viewmodel.PetsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

val appModules = module {
    single<PetsRepository> { PetsRepositoryImpl(get(), get()) }
    single { Dispatchers.IO }
    single { PetsViewModel(get()) }
    single {
        Retrofit.Builder()
            .addConverterFactory(
                json.asConverterFactory(contentType = "application/json".toMediaType())
            )
            .baseUrl("https://cataas.com/api/")
            .build()
    }
    single { get<Retrofit>().create(CatsAPI::class.java) }
}