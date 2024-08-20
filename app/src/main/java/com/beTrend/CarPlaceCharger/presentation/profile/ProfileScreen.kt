package com.beTrend.CarPlaceCharger.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.beTrend.CarPlaceCharger.CarPlaceCharger.R
import com.beTrend.CarPlaceCharger.components.SmallSpacer
import com.beTrend.CarPlaceCharger.components.TopBar
import com.beTrend.CarPlaceCharger.components.UserListItem
import com.beTrend.CarPlaceCharger.navigation.Screen
import com.beTrend.CarPlaceCharger.presentation.profile.components.RevokeAccess
import com.beTrend.CarPlaceCharger.ui.theme.BlueApp
import com.beTrend.CarPlaceCharger.ui.theme.BlueAppL
import com.beTrend.CarPlaceCharger.ui.theme.BlueAppM
import com.beTrend.CarPlaceCharger.ui.theme.sourceProFontFamily

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToSignInScreen: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val user by viewModel.user.collectAsState()
    val conectores = remember { DataConector.conectorList }
    val conector = listOf(conectores)
    var selectedOption by remember { mutableStateOf(conectores.first()) }

    //val conectores = listOf("Tipo 1 (SAE J1772)", "Tipo 2 (IEC 62196)", "GB/T 20234", "CHAdeMO", "TESLA", "Outros Carregadores")

    Scaffold(
        topBar = {
            TopBar(
                title = Screen.ProfileScreen.route,
                signOut = {
                    viewModel.signOut()
                    navigateToSignInScreen()
                }
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    if (user != null) {
                        Row(horizontalArrangement = Arrangement.Center) {
                            UserListItem(
                                user!!,
                                user!!.name + " " + user!!.lastName,
                                "${user!!.score} points",
                                imageSize = 128.dp
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(state = ScrollState(0), true),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 0.dp, 0.dp, 5.dp),
                            shape = RoundedCornerShape(corner = CornerSize(15.dp)),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 3.dp,
                            )
                        ) {
                            Column (
                                Modifier
                                    .background(BlueAppM)
                                    .fillMaxSize()
                            ){
                                Text(
                                    text = "Modelo do Carro",
                                    color = BlueApp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = sourceProFontFamily,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                SmallSpacer()
                                Row (
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Image(
                                        painter = painterResource(id = R.mipmap.ic_car),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(3.dp)
                                            .clip(RoundedCornerShape(corner = CornerSize(10.dp))),
                                        alignment = Alignment.Center
                                    )
                                    SmallSpacer()
                                    Text(
                                        text = "Nome do Carro",
                                        color = BlueApp,
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = sourceProFontFamily,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                }
                            }

                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 0.dp, 0.dp, 5.dp),
                            shape = RoundedCornerShape(corner = CornerSize(15.dp)),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 3.dp,
                            )
                        ) {
                            Column (
                                Modifier
                                    .fillMaxSize()
                                    .background(BlueAppM)
                            ){
                                Text(
                                    text = "Tipo de Conector",
                                    color = BlueApp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = sourceProFontFamily,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                SmallSpacer()
                                conectores.forEach{ conect ->
                                    Row(
                                        Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 10.dp)
                                            .selectable(
                                                selected = conect == selectedOption,
                                                onClick = { selectedOption = conect },
                                                role = Role.RadioButton
                                            ),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = conect == selectedOption,
                                            onClick = { selectedOption = conect }
                                        )
                                        Text(
                                            text = conect.name,
                                            color = BlueApp,
                                            fontWeight = FontWeight.Normal,
                                            fontFamily = sourceProFontFamily,
                                        )
                                        Spacer(
                                            Modifier
                                                .weight(1f, true)
                                                .padding(horizontal = 10.dp)
                                        )
                                        ConectorItemList(conector = conect)
                                        Spacer(
                                            Modifier
                                                .padding(end = 10.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.background(BlueAppL)
    )

    RevokeAccess(
        snackbarHostState = snackbarHostState,
        coroutineScope = coroutineScope,
        signOut = {
            viewModel.signOut()
            navigateToSignInScreen()
        }
    )



}
