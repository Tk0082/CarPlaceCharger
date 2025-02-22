package com.beTrend.CarPlaceCharger.presentation.station

import com.beTrend.CarPlaceCharger.presentation.station.network.DirectionsService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


class LocationRepository(
    private val httpClient: HttpClient,
    private val locationService: DirectionsService
) {
    suspend fun findLocation(orig: String, dest: String, key: String): DirectionsResponse {
        return locationService.getDirections(orig, dest, key)
    }
}