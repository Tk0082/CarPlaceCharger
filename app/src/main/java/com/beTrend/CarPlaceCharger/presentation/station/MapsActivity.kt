package com.beTrend.CarPlaceCharger.presentation.station

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.DisposableEffect
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
import com.beTrend.CarPlaceCharger.ui.theme.BlueApp
import com.beTrend.CarPlaceCharger.ui.theme.Graffit
import com.beTrend.CarPlaceCharger.ui.theme.sourceProFontFamily
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
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
        val posit by remember { mutableStateOf(LatLng(lat, long)) }

        // Latitude e Longitude da Localização Atual
        var currentLocation by remember { mutableStateOf(LatLng(0.0,0.0))}
        var routePath by remember { mutableStateOf<List<LatLng>>(listOf()) }  // Rota

        // Location Request
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val locationRequest = LocationRequest.create().apply {
            interval = 10000 // 10 seconds
            fastestInterval = 5000 // 5 seconds
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationRes: LocationResult) {
                super.onLocationResult(locationRes)
                locationRes.lastLocation?.let { result ->
                    currentLocation = LatLng(result.latitude, result.longitude)
                    val url = getDirectionsUrl(currentLocation, posit)
                    scope.launch {
                        routePath = fetchDirections(url)
                    }
                }
            }
        }

        LaunchedEffect(isLocationEnabled) {
            if (isLocationEnabled) {
                try {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        location?.let {
                            currentLocation = LatLng(it.latitude, it.longitude)

                            // Obter a Rota (user_local - destino)
                            val url = getDirectionsUrl(currentLocation, posit)
                            scope.launch {
                                routePath = fetchDirections(url)
                            }
                        }
                    }
                } catch (e: SecurityException){
                    Log.e("MapsActivity", "Problema de permissão:\n ${e.message}")
                }
            } else {
                currentLocation = LatLng(
                            posit.latitude,
                            posit.longitude
                        )
            }
        }

        val bounds = LatLngBounds.builder().apply {
            include(currentLocation)
            include(posit)
        }.build()

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentLocation,18f)
        }

        LaunchedEffect(currentLocation, posit) {
            cameraPositionState.move(
                CameraUpdateFactory.newLatLngBounds(bounds, 100)
            )
        }

        DisposableEffect(Unit) {
            onDispose {
                fusedLocationClient.removeLocationUpdates(locationCallback)
            }
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
        ) {
            if (isLocationEnabled != false) {
                MarkerInfoWindow(
                    state = MarkerState(position = currentLocation),
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
            if (routePath.isNotEmpty()) {
                Polyline(
                    points = routePath,
                    color = BlueApp,
                    zIndex = 1f
                )
            } else {
                Polyline(
                    points = listOf(currentLocation, posit),
                    color = BlueApp
                )
            }
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
        val strDest = "destination=${dest.latitude},${dest.longitude}"
        val key = "key=AIzaSyDQl09-TeRXqyPIg6h0G9LQYFPocyNIGsM"
        //val parameters = "$strOrigin&$strDest&$key"
        return "https://maps.googleapis.com/maps/api/directions/json?$strOrigin&$strDest&$key"
    }

    private suspend fun fetchDirections(url: String): List<LatLng>{
        return try {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                Log.e("MapsActivity", "Falha ao buscar rotas: \n${response.message}")
                return emptyList()
            }
            val data = response.body?.string() ?: return emptyList()
            val jsonObject = JSONObject(data)
            val routes = jsonObject.getJSONArray("routes")
            val legs = routes.getJSONObject(0).getJSONArray("legs")
            val steps = legs.getJSONObject(0).getJSONArray("steps")

            val path = mutableListOf<LatLng>()
            for (i in 0 until steps.length()) {
                val step = steps.getJSONObject(i)
                val polyline = step.getJSONObject("polyline").getString("points")
                //val decodePath = decodePolyline(polyline)
                val startLocation = step.getJSONObject("start_location")
                path.add(LatLng(startLocation.getDouble("lat"), startLocation.getDouble("lng")))
                val endLocation = step.getJSONObject("end_location")
                path.add(LatLng(endLocation.getDouble("lat"), endLocation.getDouble("lng")))
                path.addAll(decodePolyline(polyline))
            }
            path
        } catch (e: Exception){
            Log.e("MapsActivity", "Exceção ao buscar rotas:\n", e)
            emptyList()
        }
    }

    private fun decodePolyline(encoded: String): List<LatLng> {
        val poly = mutableListOf<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len){
            var b: Int
            var shift = 0
            var res = 0
            do {
                b = encoded[index++].code -63
                res = res or ((b and 0x1f) shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (res and 1 != 0)(res ushr 1).inv() else res ushr 1
            lat += dlat

            shift = 0
            res = 0
            do {
                b = encoded[index++].code -63
                res = res or ((b and 0x1f) shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (res and 1 != 0) (res ushr 1).inv() else res ushr 1
            lng += dlng

            val pointLat = (lat / 1E5).toDouble()
            val pointLng = (lng / 1E5).toDouble()
            poly.add(LatLng(pointLat, pointLng))
        }
        return poly
    }
}

