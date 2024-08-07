package com.beTrend.CarPlaceCharger

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EvStation
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.beTrend.CarPlaceCharger.navigation.Screen

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Leaderboard : BottomBarScreen(
        route = Screen.StationListScreen.route,
        title = Screen.StationListScreen.route,
        icon = Icons.Outlined.EvStation
    )

    object Home : BottomBarScreen(
        route = Screen.HomeScreen.route,
        title = Screen.HomeScreen.route,
        icon = Icons.Outlined.LocationOn
    )

    object Profile : BottomBarScreen(
        route = Screen.ProfileScreen.route,
        title = Screen.ProfileScreen.route,
        icon = Icons.Outlined.Person
    )
}