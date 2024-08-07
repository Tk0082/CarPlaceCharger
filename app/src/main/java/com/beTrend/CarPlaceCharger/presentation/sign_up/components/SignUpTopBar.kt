package com.beTrend.CarPlaceCharger.presentation.sign_up.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.beTrend.CarPlaceCharger.components.BackIcon
import com.beTrend.CarPlaceCharger.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpTopBar(
    navigateBack: () -> Unit
) {
    TopAppBar (
        title = {
            Text(
                text = Screen.SignUpScreen.route
            )
        },
        navigationIcon = {
            BackIcon(
                navigateBack = navigateBack
            )
        }
    )
}