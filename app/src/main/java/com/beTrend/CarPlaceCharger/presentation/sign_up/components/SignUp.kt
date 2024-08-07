package com.beTrend.CarPlaceCharger.presentation.sign_up.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.beTrend.CarPlaceCharger.components.ProgressBar
import com.beTrend.CarPlaceCharger.domain.model.Response
import com.beTrend.CarPlaceCharger.presentation.sign_up.SignUpViewModel

@Composable
fun SignUp(
    viewModel: SignUpViewModel = hiltViewModel(),
    sendEmailVerification: () -> Unit,
    showVerifyEmailMessage: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    when(val signUpResponse = viewModel.signUpResponse) {
        is Response.None -> {}
        is Response.Loading -> ProgressBar()
        is Response.Success -> {
            val isUserSignedUp = signUpResponse.data?.user != null
            LaunchedEffect(isUserSignedUp) {
                if (isUserSignedUp) {
                    sendEmailVerification()
                    showVerifyEmailMessage()

                    navigateToHomeScreen()
                }
            }
        }
        is Response.Failure -> signUpResponse.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }
    }
}