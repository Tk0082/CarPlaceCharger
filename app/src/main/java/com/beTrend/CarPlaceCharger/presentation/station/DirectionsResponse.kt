package com.beTrend.CarPlaceCharger.presentation.station

import com.squareup.moshi.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("PLUGIN_IS_NOT_ENABLED")
@Serializable
class DirectionsResponse (
    @SerialName("origin")
    @field:Json(name = "origin")
    private val orig: String,
    @SerialName("destination")
    @field:Json(name = "destination")
    private val dest: String,
    @SerialName("key")
    @field:Json(name = "key")
    private val key: String
)
//data class DirectionsResponse(
//    val routes: List<Route>
//)
//
//data class Route(
//    val overviewpolyline: Polyline
//)
//
//data class Polyline(
//    val points: String
//)