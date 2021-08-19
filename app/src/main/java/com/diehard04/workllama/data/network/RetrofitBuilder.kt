package com.diehard04.workllama.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by DieHard_04 on 14-08-2021.
 */
object RetrofitBuilder {

    private const val BASE_URL = "https://iie-service-dev.workingllama.com/iie-service/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}