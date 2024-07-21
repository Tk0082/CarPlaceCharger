package com.beTrend.CarPlaceCharger.presentation.verify_email

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.beTrendM.CarPlaceCharger.components.TopBar
import com.beTrendM.CarPlaceCharger.core.Strings.EMAIL_NOT_VERIFIED_MESSAGE
import com.beTrendM.CarPlaceCharger.core.Utils.Companion.showToast
import com.beTrendM.CarPlaceCharger.navigation.Screen
import com.beTrendM.CarPlaceCharger.presentation.profile.ProfileViewModel
import com.beTrendM.CarPlaceCharger.presentation.profile.components.RevokeAccess
import com.beTrendM.CarPlaceCharger.presentation.verify_email.ReloadUser
import com.beTrendM.CarPlaceCharger.presentation.verify_email.VerifyEmailContent

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
