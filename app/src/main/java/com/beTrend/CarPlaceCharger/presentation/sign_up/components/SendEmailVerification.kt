package com.beTrend.CarPlaceCharger.presentation.sign_up.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.beTrend.CarPlaceCharger.components.ProgressBar
import com.beTrend.CarPlaceCharger.domain.model.Response
import com.beTrend.CarPlaceCharger.presentation.sign_up.SignUpViewModel

@Composable
fun SendEmailVerification(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    when(val sendEmailVerificationResponse = viewModel.sendEmailVerificationResponse) {
        is Response.None -> Unit
        is Response.Loading -> ProgressBar()
        is Response.Success -> Unit
        is Response.Failure -> sendEmailVerificationResponse.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }
    }
}