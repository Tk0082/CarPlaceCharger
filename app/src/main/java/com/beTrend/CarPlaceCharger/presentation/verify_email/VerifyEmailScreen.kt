package com.beTrend.CarPlaceCharger.presentation.verify_email

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.beTrend.CarPlaceCharger.components.TopBar
import com.beTrend.CarPlaceCharger.core.Strings.EMAIL_NOT_VERIFIED_MESSAGE
import com.beTrend.CarPlaceCharger.core.Utils.Companion.showToast
import com.beTrend.CarPlaceCharger.navigation.Screen
import com.beTrend.CarPlaceCharger.presentation.profile.ProfileViewModel
import com.beTrend.CarPlaceCharger.presentation.profile.components.RevokeAccess

@Composable
fun VerifyEmailScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToProfileScreen: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(
                title = Screen.VerifyEmailScreen.route,
                signOut = {
                    viewModel.signOut()
                }
            )
        },
        content = { padding ->
            VerifyEmailContent(
                padding = padding,
                reloadUser = {
                    viewModel.reloadUser()
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    )

    ReloadUser(
        navigateToProfileScreen = {
            if (viewModel.isEmailVerified) {
                navigateToProfileScreen()
            } else {
                showToast(context, EMAIL_NOT_VERIFIED_MESSAGE)
            }
        }
    )

    RevokeAccess(
        snackbarHostState = snackbarHostState,
        coroutineScope = coroutineScope,
        signOut = {
            viewModel.signOut()
        }
    )
}
