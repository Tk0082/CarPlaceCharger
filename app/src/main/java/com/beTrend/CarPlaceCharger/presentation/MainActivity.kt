package com.beTrend.CarPlaceCharger.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.beTrend.CarPlaceCharger.CarPlaceCharger.R
import com.beTrend.CarPlaceCharger.components.BottomBar
import com.beTrend.CarPlaceCharger.navigation.NavGraph
import com.beTrend.CarPlaceCharger.presentation.leaderboard.StationViewModel
import com.beTrend.CarPlaceCharger.presentation.station.MapsActivity
import com.beTrend.CarPlaceCharger.presentation.station.ShowMessage
import com.beTrend.CarPlaceCharger.presentation.station.StationListItem
import com.beTrend.CarPlaceCharger.ui.theme.CarPlaceCharger
import com.beTrend.CarPlaceCharger.ui.theme.Green50
import com.beTrend.CarPlaceCharger.ui.theme.Green80
import com.beTrend.CarPlaceCharger.ui.theme.sourceProFontFamily
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    private lateinit var i: Intent
    private lateinit var navController: NavHostController
    private val viewModel by viewModels<MainViewModel>()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            CarPlaceCharger {
                Scaffold(
                    bottomBar = { BottomBar(navController = navController) }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding))
                    {
                        NavGraph(
                            navController = navController
                        )
                    }
                }
            }
        }
    }


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun PowerAppView(viewModel: StationViewModel) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                CheckLocalPermission()
                TopBar()
                StationsContent(viewModel)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    @Composable
    fun TopBar() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Green50)
                .offset(10.dp, 5.dp)
                .height(65.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.padding(5.dp))
            Image(
                painter = painterResource(id = R.mipmap.ico_energy),
                contentDescription = null,
                Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .padding(horizontal = 3.dp)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                color = Green80,
                text = "PowerUp",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = sourceProFontFamily
                )
            )
        }
    }

    @Composable
    fun StationsContent(viewModel: StationViewModel) {
        // Guarda o estado da lista
        val listState = rememberLazyListState()

        DisposableEffect(Unit) {
            lifecycleScope.launch {
                scrollToTop(listState)
            }

            onDispose { }
        }

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(vertical = 5.dp, horizontal = 8.dp)
        ) {
            items(
                items = viewModel.stationList,
            ) { station ->
                StationListItem(station = station)
            }
        }
    }

    private suspend fun scrollToTop(listState: LazyListState) {
        listState.scrollToItem(0)
    }


    // Permissão de Localização
    @Composable
    fun RequestLocalPermission() {
        val context = LocalContext.current
        var showDialog by remember { mutableStateOf(true) }

        // Abrir Página de Configurações do App
        val openSettingsApp =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}

        if (showDialog) {
            AlertDialog(
                title = { Text(text = "Permitir Localização") },
                text = { Text(text = "Este Aplicativo precisa de permissão de localização para funcionar corretamente.\nPor favor, conceda a permissão nas configurações.") },
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(onClick = {
                        i = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                        openSettingsApp.launch(i)
                        showDialog = false
                    }) {
                        Text(text = "Abrir Configuração")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false
                        finish()
                    }) {
                        Text(text = "Cancelar")
                    }
                }
            )
        }
    }

    // Checar Permissão de Localização
    @Composable
    fun CheckLocalPermission() {
        val context = LocalContext.current
        var permissionGranted by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            permissionGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PermissionChecker.PERMISSION_GRANTED
        }
        if (!permissionGranted) {
            RequestLocalPermission()
        } else {
            val msg = ShowMessage(context, "Localização Permitida!")
            i = Intent(this, MapsActivity::class.java)
            msg.showToast()
        }
    }

    override fun onDestroy() {
        finish()
        super.onDestroy()
    }
}
