package com.beTrend.CarPlaceCharger.presentation.station.network

import com.beTrend.CarPlaceCharger.presentation.station.DirectionsResponse
import retrofit2.http.GET
import retrofit2.http.Path

// Interface de requisições do Retroffit
interface DirectionsService {
    @GET("json?origin={origin}&destination={destination}&key={key}")
    suspend fun getDirections(
        @Path("origin") origin: String,
        @Path("destination") destination: String,
        @Path("key") apiKey: String
    ): DirectionsResponse
}
