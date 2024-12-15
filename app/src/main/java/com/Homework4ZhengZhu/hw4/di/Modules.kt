package com.Homework4ZhengZhu.hw4.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.Homework4ZhengZhu.hw4.data.BitcoinsAPI
import com.Homework4ZhengZhu.hw4.data.BitcoinsRepository
import com.Homework4ZhengZhu.hw4.data.BitcoinsRepositoryImpl
import com.Homework4ZhengZhu.hw4.viewmodel.BitcoinsViewModel
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
    single<BitcoinsRepository> { BitcoinsRepositoryImpl(get(), get()) }
    single { Dispatchers.IO }
    single { BitcoinsViewModel(get()) }
    single {
        Retrofit.Builder()
            .addConverterFactory(
                json.asConverterFactory(contentType = "application/json".toMediaType())
            )
            .baseUrl("https://cataas.com/api/")
            .build()
    }
    single { get<Retrofit>().create(BitcoinsAPI::class.java) }
}