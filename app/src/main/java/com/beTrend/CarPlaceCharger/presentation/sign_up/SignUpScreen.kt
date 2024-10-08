package com.beTrend.CarPlaceCharger.presentation.sign_up

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.beTrend.CarPlaceCharger.core.Strings.VERIFY_EMAIL_MESSAGE
import com.beTrend.CarPlaceCharger.core.Utils.Companion.showToast
import com.beTrend.CarPlaceCharger.presentation.sign_up.SignUpViewModel
import com.beTrend.CarPlaceCharger.presentation.sign_up.components.SendEmailVerification
import com.beTrend.CarPlaceCharger.presentation.sign_up.components.SignUp
import com.beTrend.CarPlaceCharger.presentation.sign_up.components.SignUpContent
import com.beTrend.CarPlaceCharger.presentation.sign_up.components.SignUpTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ExperimentalComposeUiApi
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            SignUpTopBar(
                navigateBack = navigateBack
            )
        },
        content = { padding ->
            SignUpContent(
                padding = padding,
                signUpPassenger = { email, password, phone, firstName, lastName, userType, localImageUri ->
                    viewModel.registerAndCreateUser(
                        email,
                        password,
                        firstName,
                        lastName,
                        phone,
                        userType,
                        localImageUri
                    )
                },
                navigateBack = navigateBack
            )
        }
    )

    SignUp(
        sendEmailVerification = {
            viewModel.sendEmailVerification()
        },
        showVerifyEmailMessage = {
            showToast(context, VERIFY_EMAIL_MESSAGE)
        },
        navigateToHomeScreen = {
            navigateToHomeScreen()
        }
    )

    SendEmailVerification()
}