package com.beTrend.CarPlaceCharger.presentation.station

import com.beTrend.CarPlaceCharger.presentation.station.network.DirectionsService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitInstance {

    private const val BASE_URL = "https://maps.googleapis.com/maps/api/directions/"

    val api: DirectionsService by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(DirectionsService::class.java)
    }
}
