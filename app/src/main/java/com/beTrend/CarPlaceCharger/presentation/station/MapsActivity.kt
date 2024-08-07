// Copyright 2024 - BeTrendMobileCreations CarPlace Charger
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.beTrend.CarPlaceCharger.presentation.station

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.beTrend.CarPlaceCharger.CarPlaceCharger.R
import com.beTrend.CarPlaceCharger.ui.theme.BackCardD
import com.beTrend.CarPlaceCharger.ui.theme.BackCardL
import com.beTrend.CarPlaceCharger.ui.theme.BackCircle
import com.beTrend.CarPlaceCharger.ui.theme.BlueApp
import com.beTrend.CarPlaceCharger.ui.theme.Graffit
import com.beTrend.CarPlaceCharger.ui.theme.sourceProFontFamily
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class MapsActivity : ComponentActivity(){

    private lateinit var i: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lat = intent.getDoubleExtra("lat", 0.0)
        val long = intent.getDoubleExtra("long", 0.0)
        val name = intent.getStringExtra("name")!!
        val desc = intent.getStringExtra("desc")!!

        setContent {
            window.statusBarColor = getColor(R.color.blueapp)
            MapsScreen(name, desc, lat, long)
        }
    }


    @SuppressLint("MissingPermission")
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun MapsScreen(name: String, desc: String, lat: Double, long: Double) {

        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = true)) }
        val properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }

        // Cores para formar o Gradient do PopUp
        val colors = listOf(BackCardD, BackCardL)
        val brush = Brush.verticalGradient(colors)   // Brush Vertical do Gradient

        var isLocationEnabled by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            isLocationEnabled = isLocationEnabled(context)
        }

        // Latitude e Longitude do ponto(Y, X)
        val posit = LatLng(lat, long)

        // Latitude e Longitude da Localização Atual
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val routePath = remember { mutableStateOf(listOf<LatLng>()) }  // Rota
        var currentLocation = remember { mutableStateOf(LatLng(0.0,0.0))}
        if (isLocationEnabled == false){
            currentLocation = remember { mutableStateOf(LatLng(posit.latitude,posit.longitude)) }   //-13.150424, -51.819067)) }  // Padrão Centro do Brasil
        } else {
            LaunchedEffect(true) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null){
                        currentLocation.value = LatLng(location.latitude, location.longitude)

                        // Obter a Rota
                        val url = getDirectionsUrl(currentLocation.value, posit)
                        scope.launch {
                            routePath.value = fetchDirections(url)
                        }
                    } else {
                        currentLocation.value
                    }
                }
            }
        }

        /*val boundsBuilder = LatLngBounds.builder()
        val coordinates = listOf(
            LatLng(currentLocation.value.latitude, currentLocation.value.longitude),
            LatLng(posit.latitude, posit.longitude)
        )
        for (coordinate in coordinates) {
            boundsBuilder.include(coordinate)
        }

        val bounds = boundsBuilder.build()

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentLocation.value,10f)
        }

        LaunchedEffect(true) {
            cameraPositionState.move(
                update = CameraUpdateFactory.newLatLngBounds(bounds, 100)
            )
        }*/
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentLocation.value,15f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
        ) {
            Circle(
                center = currentLocation.value,
                radius = 500.0,
                strokeWidth = 2f,
                strokeColor = BackCardD,
                fillColor = BackCircle
            )
            if (isLocationEnabled != false) {
                MarkerInfoWindow(
                    state = MarkerState(position = currentLocation.value),
                    draggable = false,
                    icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin_1),
                    zIndex = 1f
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .border(
                                BorderStroke(1.dp, BackCardD),
                                RoundedCornerShape(10)
                            )
                            .clip(RoundedCornerShape(10))
                            .background(brush)
                            .border(1.dp, BackCardD)
                            .padding(20.dp, 10.dp)
                    ) {
                        Text("Sua Localização", fontWeight = FontWeight.Bold, color = BlueApp, fontFamily = sourceProFontFamily)
                    }
                }
                MarkerInfoWindow(
                    state = MarkerState(position = posit),
                    draggable = false,
                    icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin),
                    zIndex = 1f
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .border(
                                BorderStroke(1.dp, BackCardD),
                                RoundedCornerShape(10)
                            )
                            .clip(RoundedCornerShape(10))
                            .background(brush)
                            .border(1.dp, BackCardD)
                            .padding(20.dp, 10.dp)
                    ) {
                        Text(name, fontWeight = FontWeight.Bold, color = BlueApp, fontFamily = sourceProFontFamily)
                        Text(desc, fontWeight = FontWeight.Medium, color = Graffit, fontFamily = sourceProFontFamily)
                    }
                }
            } else {
                MarkerInfoWindow(
                    state = MarkerState(position = posit),
                    draggable = false,
                    icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin),
                    zIndex = 1f
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .border(
                                BorderStroke(1.dp, BackCardD),
                                RoundedCornerShape(10)
                            )
                            .clip(RoundedCornerShape(10))
                            .background(brush)
                            .border(1.dp, BackCardD)
                            .padding(20.dp, 10.dp)
                    ) {
                        Text(name, fontWeight = FontWeight.Bold, color = BlueApp, fontFamily = sourceProFontFamily)
                        Text(desc, fontWeight = FontWeight.Bold, color = BlueApp, fontFamily = sourceProFontFamily)
                    }
                }
            }
            Polyline(
                points = routePath.value,
                color = BlueApp
            )
        }
    }

    fun isLocationEnabled(context: Context): Boolean{
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    fun MarkerOptions.icon(context: Context, @DrawableRes vectorDrawable: Int): MarkerOptions {
        this.icon(ContextCompat.getDrawable(context, vectorDrawable)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            draw(android.graphics.Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        })
        return this
    }

    private fun getDirectionsUrl(origin: LatLng, dest: LatLng): String {
        val strOrigin = "origin=${origin.latitude},${origin.longitude}"
        val strDest = "destinaton=${dest.latitude},${dest.longitude}"
        val sensor = "sensor=false"
        val key = "key=AIzaSyDQl09-TeRXqyPIg6h0G9LQYFPocyNIGsM"
        val parameters = "$strOrigin&$strDest&$sensor&$key"
        return "https://maps.googleapis.com/maps/api/directions/json?$parameters"
    }

    private suspend fun fetchDirections(url: String): List<LatLng>{
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        val data = response.body?.string() ?: return  emptyList()

        val jsonObject = JSONObject(data)
        val routes = jsonObject.getJSONArray("routes")
        val legs = routes.getJSONObject(0).getJSONArray("legs")
        val steps = legs.getJSONObject(0).getJSONArray("steps")

        val path = mutableListOf<LatLng>()

        for (i in 0 until steps.length()){
            val step = steps.getJSONObject(i)
            val startLocation = step.getJSONObject("start_location")
            path.add(LatLng(startLocation.getDouble("lat"), startLocation.getDouble("lng")))
            val endLocation = step.getJSONObject("end_location")
            path.add(LatLng(endLocation.getDouble("lat"), endLocation.getDouble("lng")))
        }
        return path
    }

}

