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
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.beTrend.CarPlaceCharger.CarPlaceCharger.R
import com.beTrend.CarPlaceCharger.ui.theme.BackCardD
import com.beTrend.CarPlaceCharger.ui.theme.BackCardL
import com.beTrend.CarPlaceCharger.ui.theme.BackCircle
import com.beTrend.CarPlaceCharger.ui.theme.Graffit
import com.beTrend.CarPlaceCharger.ui.theme.Graffitd
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberCameraPositionState

class MapsActivity : ComponentActivity() {

    private lateinit var localCurrent: FusedLocationProviderClient
    private lateinit var i: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lat = intent.getDoubleExtra("lat", 0.0)
        val long = intent.getDoubleExtra("long", 0.0)
        val name = intent.getStringExtra("name")!!
        val desc = intent.getStringExtra("desc")!!

        setContent {
            window.statusBarColor = getColor(R.color.greend)
            MapsScreen(name, desc, lat, long)
        }
    }


    @Composable
    fun MapsScreen(name: String, desc: String, lat: Double, long: Double) {

        // Cores para formar o Gradient
        val colors = listOf(BackCardD, BackCardL)
        // Brush Vertical do Gradient
        val brush = Brush.verticalGradient(colors)

        val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = true)) }
        val properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }

        // Latitude e Longitude (Y, X)
        val posit = LatLng(lat, long)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(posit, 15.5f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings
        ) {
            Circle(
                center = posit,
                radius = 500.0,
                strokeWidth = 2f,
                strokeColor = BackCardD,
                fillColor = BackCircle
            )
            Marker(
                flat = true,
                draggable = true,
                zIndex = 1f
            )
            MarkerInfoWindow(
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
                        .padding(20.dp)
                ) {
                    Text(name, fontWeight = FontWeight.Bold, color = Graffitd)
                    Text(desc, fontWeight = FontWeight.Medium, color = Graffit)
                }
            }
        }
    }
}