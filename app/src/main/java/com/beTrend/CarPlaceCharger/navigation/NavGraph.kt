package com.beTrendM.CarPlaceCharger.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import com.beTrend.CarPlaceCharger.presentation.home.HomeScreen
import com.beTrend.CarPlaceCharger.presentation.station.StationScreen
import com.beTrendM.CarPlaceCharger.presentation.profile.ProfileScreen
import com.beTrendM.CarPlaceCharger.presentation.sign_in.SignInScreen
import com.beTrendM.CarPlaceCharger.presentation.sign_up.SignUpScreen
import com.beTrend.CarPlaceCharger.presentation.verify_email.VerifyEmailScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@Composable
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
fun NavGraph(
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.SignInScreen.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(
            route = Screen.SignInScreen.route
        ) {
            SignInScreen(
                navigateToForgotPasswordScreen = {
                    navController.navigate(Screen.ForgotPasswordScreen.route)
                },
                navigateToSignUpScreen = {
                    navController.navigate(Screen.SignUpScreen.route)
                },
                navigateToHomeScreen = {
                    navController.navigate(Screen.HomeScreen.route)
                }
            )
        }
        composable(
            route = Screen.SignUpScreen.route
        ) {
            SignUpScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToHomeScreen = {
                    navController.navigate(Screen.HomeScreen.route)
                }
            )
        }
        composable(
            route = Screen.VerifyEmailScreen.route
        ) {
            VerifyEmailScreen(
                navigateToProfileScreen = {
                    navController.navigate(Screen.ProfileScreen.route)
                }
            )
        }
        composable(
            route = Screen.ProfileScreen.route
        ) {
            ProfileScreen(
                navigateToSignInScreen = {
                    navController.navigate(Screen.SignInScreen.route)
                }
            )
        }
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen()
        }
        composable(
            route = Screen.StationListScreen.route
        ) {
            StationScreen()
        }
    }
}
