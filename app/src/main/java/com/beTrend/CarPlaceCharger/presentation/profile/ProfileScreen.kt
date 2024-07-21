package com.beTrendM.CarPlaceCharger.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.beTrendM.CarPlaceCharger.components.TopBar
import com.beTrendM.CarPlaceCharger.components.UserListItem
import com.beTrendM.CarPlaceCharger.navigation.Screen
import com.beTrendM.CarPlaceCharger.presentation.profile.components.RevokeAccess

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToSignInScreen: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val user by viewModel.user.collectAsState()

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
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
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
