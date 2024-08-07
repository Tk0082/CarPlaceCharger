package com.beTrend.CarPlaceCharger.presentation.sign_in

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.beTrend.CarPlaceCharger.core.Utils.Companion.showToast
import com.beTrend.CarPlaceCharger.presentation.sign_in.SignInViewModel
import com.beTrend.CarPlaceCharger.presentation.sign_in.components.SignIn
import com.beTrend.CarPlaceCharger.presentation.sign_in.components.SignInContent
import com.beTrend.CarPlaceCharger.presentation.sign_in.components.SignInTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ExperimentalComposeUiApi
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            SignInTopBar()
        },
        content = { padding ->
            SignInContent(
                padding = padding,
                signIn = { email, password ->
                    viewModel.signInWithEmailAndPassword(email, password)
                },
                navigateToSignUpScreen = navigateToSignUpScreen
            )
        }
    )

    SignIn(
        showErrorMessage = { errorMessage ->
            showToast(context, errorMessage)
        },
        navigateToHomeScreen = {
            navigateToHomeScreen()
        }
    )
}