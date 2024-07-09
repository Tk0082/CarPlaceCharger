
// Copyright 2024 Google LLC
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

package com.betrendmobile.carplacecharger

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
import com.betrendmobile.carplacecharger.ui.theme.BackCardD
import com.betrendmobile.carplacecharger.ui.theme.BackCardL
import com.betrendmobile.carplacecharger.ui.theme.BackCircle
import com.betrendmobile.carplacecharger.ui.theme.Graffit
import com.betrendmobile.carplacecharger.ui.theme.Graffitd
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberCameraPositionState

class MapsActivity : ComponentActivity() {

    private lateinit var localCurrent: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapsScreen()
        }
    }
}

@Composable
fun MapsScreen(){
    val colors = listOf(BackCardD, BackCardL)
    val brush = Brush.verticalGradient(colors)

    // locationPermissionState = remember { Manifest.permission.ACCESS_FINE_LOCATION }
    val uiSettings by remember { mutableStateOf(MapUiSettings( zoomControlsEnabled = true)) }
    val properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }
    //var isMapLoaded by remember { mutableStateOf(false) }

    val posit = LatLng(-16.7415469,-49.2769324) // (Y, X)
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(posit, 15.5f)
    }


    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings
    ){
        Circle(
            center = posit,
            radius = 500.0,
            strokeWidth = 1f,
            strokeColor = BackCardD,
            fillColor = BackCircle
        )
        MarkerInfoWindow(
            position = posit,
            //title = "Goiânia",
            //snippet = "Eletroposto em Goiânia"
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
                    .padding(20.dp)
            ){
                Text("Goiânia", fontWeight = FontWeight.Bold, color = Graffitd)
                Text("Eletroposto em Goiânia", fontWeight = FontWeight.Medium, color = Graffit)
            }
        }
    }
}