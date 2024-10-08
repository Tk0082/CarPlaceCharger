package com.beTrend.CarPlaceCharger.presentation.sign_in.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.beTrend.CarPlaceCharger.components.ProgressBar
import com.beTrend.CarPlaceCharger.domain.model.Response
import com.beTrend.CarPlaceCharger.presentation.sign_in.SignInViewModel

@Composable
fun SignIn(
    viewModel: SignInViewModel = hiltViewModel(),
    showErrorMessage: (errorMessage: String?) -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    when (val signInResponse = viewModel.signInResponse) {
        is Response.None -> {}
        is Response.Loading -> ProgressBar()
        is Response.Success<*> -> {
            navigateToHomeScreen()
        }

        is Response.Failure -> signInResponse.apply {
            LaunchedEffect(e) {
                print(e)
                showErrorMessage(e.message)
            }
        }
    }
}