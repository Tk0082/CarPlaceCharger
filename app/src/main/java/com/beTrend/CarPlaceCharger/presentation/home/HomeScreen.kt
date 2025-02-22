package com.beTrend.CarPlaceCharger.presentation.home

//noinspection UsingMaterialAndMaterial3Libraries
import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.sharp.MyLocation
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.beTrend.CarPlaceCharger.CarPlaceCharger.R
import com.beTrend.CarPlaceCharger.components.LocationUpdates
import com.beTrend.CarPlaceCharger.components.SmallSpacer
import com.beTrend.CarPlaceCharger.components.UserListItem
import com.beTrend.CarPlaceCharger.core.Strings.LOG_TAG
import com.beTrend.CarPlaceCharger.core.Utils.Companion.bitmapDescriptorFromVector
import com.beTrend.CarPlaceCharger.domain.model.Passenger
import com.beTrend.CarPlaceCharger.domain.model.SearchFilters
import com.beTrend.CarPlaceCharger.domain.model.UserType
import com.beTrend.CarPlaceCharger.ui.theme.BackCardD
import com.beTrend.CarPlaceCharger.ui.theme.BackCircle
import com.beTrend.CarPlaceCharger.ui.theme.BlueApp
import com.beTrend.CarPlaceCharger.ui.theme.BlueAppBackL
import com.beTrend.CarPlaceCharger.ui.theme.BlueAppL
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
@OptIn(
    ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val nis = LatLng(-13.0272282, -52.9095716)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(nis, 5f)
    }
    var currentLocation by remember {
        mutableStateOf<Location?>(null)
    }

    /*val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Expanded
        )
    )*/

    var query by rememberSaveable { mutableStateOf("") }
    val radiusRange = 0F..1000F
    var radius by remember { mutableIntStateOf(radiusRange.endInclusive.toInt()) }
    var rating by remember { mutableIntStateOf(1) }

    val usersOnMap by viewModel.usersOnMap.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    ) {
    }
    if (locationPermissionState.allPermissionsGranted) {
        LocationUpdates(onLocationUpdate = {
            currentLocation = it.lastLocation
            viewModel.updateUserLocation(it)
        })
    }
    LaunchedEffect(Unit) {
        locationPermissionState.launchMultiplePermissionRequest()
    }
    var showPassengerInfoDialog by remember { mutableStateOf(false) }
    var selectedPassenger: Passenger? = null

    DisposableEffect(Unit)
    {
        val usersListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // snapshot value may be null
                if (snapshot.value != null) {
                    Log.i(LOG_TAG, "DisposableEffect, filtering")

                    viewModel.loadFilteredUsers(SearchFilters(query, radius, rating))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(LOG_TAG, "usersListener:onCancelled", error.toException())
            }
        }
        viewModel.userRepo.users.addValueEventListener(usersListener)

        onDispose {
            Log.i(LOG_TAG, "onDispose")
            viewModel.userRepo.users.removeEventListener(usersListener)
        }
    }

    Scaffold(content = { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BlueAppL),
            contentAlignment = Alignment.TopCenter
        ) {
            val bottomSheetScaffoldState =  rememberBottomSheetScaffoldState(
                bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
            )
            BottomSheetScaffold(
                sheetBackgroundColor = BlueAppL,
                scaffoldState = bottomSheetScaffoldState,
                sheetContent = {
                    var active by rememberSaveable { mutableStateOf(false) }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable(
                                onClick = {
                                    scope.launch {
                                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                            bottomSheetScaffoldState.bottomSheetState.expand()
                                        } else {
                                            bottomSheetScaffoldState.bottomSheetState.collapse()
                                        }
                                    }
                                })
                    ) {
                        Icon(
                            imageVector = when (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                                true -> Icons.Outlined.ExpandMore
                                false -> Icons.Outlined.ExpandLess
                            },
                            contentDescription = null,
                            tint = BlueApp
                        )
                        Row {
                            Icon(
                                Icons.Outlined.Tag,
                                modifier = Modifier
                                    .size(18.dp, 18.dp)
                                    .align(Alignment.CenterVertically),
                                contentDescription = null,
                                tint = BlueApp
                            )
                            SmallSpacer()
                            if(bottomSheetScaffoldState.bottomSheetState.isExpanded){
                                Text("Fechar Pesquisa", color = BlueApp)
                            } else {
                                Text("Abrir Pesquisa", color = BlueApp)
                            }
                        }
                        SmallSpacer()
                        DockedSearchBar(
                            modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 10.dp),
                            active = active,
                            onActiveChange = { active = it },
                            onQueryChange = {
                                query = it
                                viewModel.loadFilteredUsers(
                                    SearchFilters(
                                        query, radius, rating
                                    )
                                )
                            },
                            onSearch = { active = false },
                            query = query,
                            placeholder = { Text("Buscar", color = BackCardD) },
                            leadingIcon = {
                                Icon(
                                    Icons.Rounded.Search,
                                    tint = BlueApp,
                                    contentDescription = null
                                )
                            },
                            colors = SearchBarDefaults.colors(
                                containerColor = BlueAppBackL,
                                dividerColor = BlueApp,
                                inputFieldColors = androidx.compose.material3.TextFieldDefaults.colors(
                                    focusedTextColor = BlueApp
                                )
                            )
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(padding)
                            ) {
                                items(usersOnMap.take(3)) { user ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(8.dp)
                                    ) {
                                        UserListItem(
                                            user,
                                            user.name + " " + user.lastName,
                                            "${user.score} points"
                                        )
                                    }
                                }
                            }
                        }
                        SmallSpacer()
                        Text("Raio de alcance", color = BlueApp)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "${radiusRange.start.toInt()} m", color = BlueApp)
                            Text(text = "${radiusRange.endInclusive.toInt()} m", color = BlueApp)
                        }

                        Slider(
                            value = radius.toFloat(),
                            onValueChange = { newValue ->
                                radius = newValue.toInt()

                                viewModel.loadFilteredUsers(
                                    SearchFilters(
                                        query, radius, rating
                                    )
                                )
                            },
                            valueRange = radiusRange,
                            steps = 20,
                        )
                        Text(text = "$radius m", color = BlueApp)

                        if (currentUser?.userType == UserType.Pessoal) {
                            Slider(
                                value = rating.toFloat(),
                                onValueChange = { newValue ->
                                    rating = newValue.toInt()
                                    viewModel.loadFilteredUsers(
                                        SearchFilters(
                                            query, radius, rating
                                        )
                                    )
                                },
                                valueRange = 1F..5F,
                                steps = 5,
                            )
                            Text(text = "$rating stars", color = BlueApp)
                        }
                    }
//                    }
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 0.dp)
                ) {
                    if (locationPermissionState.allPermissionsGranted) {
                        GoogleMap(
                            modifier = Modifier.fillMaxSize(),
                            cameraPositionState = cameraPositionState,
                            uiSettings = MapUiSettings(
                                mapToolbarEnabled = true,
                                zoomControlsEnabled = false,
                                myLocationButtonEnabled = false
                            ),
                            properties = MapProperties(
                                isMyLocationEnabled = true
                            )
                        ) {
                            if (currentLocation != null) {
                                Circle(
                                    center = LatLng(
                                        currentLocation!!.latitude,
                                        currentLocation!!.longitude
                                    ),
                                    strokeWidth = 2f,
                                    strokeColor = BackCardD,
                                    fillColor = BackCircle,
                                    visible = true,
                                    radius = radius.toDouble()
                                )
                            }
                            usersOnMap.forEach { user ->
                                if (currentUser?.id != user.id) {
                                    Marker(
                                        state = MarkerState(
                                            LatLng(
                                                user.latitude,
                                                user.longitude
                                            )
                                        ),
                                        icon = when (user.userType) {
                                            UserType.Pessoal -> bitmapDescriptorFromVector(
                                                context,
                                                R.mipmap.ic_pin_1 //R.drawable.baseline_person_pin_circle_36
                                            )
                                        },
                                        onClick = {
                                            when (user.userType) {
                                                UserType.Pessoal -> {
                                                    selectedPassenger = user as Passenger
                                                    showPassengerInfoDialog = true
                                                    Log.i(LOG_TAG, selectedPassenger.toString())
                                                    Log.i(
                                                        LOG_TAG,
                                                        showPassengerInfoDialog.toString()
                                                    )
                                                }
                                            }
                                            true
                                        },
                                        title = "${user.name} ${user.lastName}"
                                    )
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 92.dp, end = 32.dp)
                    ) {
                        SmallFloatingActionButton(
                            onClick = {
                                scope.launch {
                                    if (currentLocation != null) {
                                        cameraPositionState.animate(
                                            update = CameraUpdateFactory.newLatLngZoom(
                                                LatLng(
                                                    currentLocation!!.latitude,
                                                    currentLocation!!.longitude
                                                ),
                                                16f
                                            ),
                                            durationMs = 667
                                        )
                                    }
                                }
                            },
                            shape = CircleShape,
                            modifier = Modifier
                                .size(56.dp)
                                .align(Alignment.BottomEnd),
                            content = {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    imageVector = Icons.Sharp.MyLocation,
                                    contentDescription = "Location",
                                    tint = BlueApp
                                )
                            }
                        )
                    }
                }
            }
        }
    })
}

