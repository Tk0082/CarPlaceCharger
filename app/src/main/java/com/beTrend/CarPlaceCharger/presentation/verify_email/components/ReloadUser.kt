package com.beTrend.CarPlaceCharger.presentation.verify_email

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.beTrend.CarPlaceCharger.components.ProgressBar
import com.beTrend.CarPlaceCharger.domain.model.Response
import com.beTrend.CarPlaceCharger.presentation.profile.ProfileViewModel

@Composable
fun ReloadUser(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToProfileScreen: () -> Unit
) {
    when(val reloadUserResponse = viewModel.reloadUserResponse) {
        is Response.None -> {}
        Response.Loading -> ProgressBar()
        is Response.Success -> {
            val isUserReloaded = reloadUserResponse.data
            LaunchedEffect(isUserReloaded) {
                if (isUserReloaded) {
                    navigateToProfileScreen()
                }
            }
        }
        is Response.Failure -> reloadUserResponse.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }
    }
}