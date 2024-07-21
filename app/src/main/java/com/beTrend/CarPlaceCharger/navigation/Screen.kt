package com.beTrendM.CarPlaceCharger.navigation

sealed class Screen(val route: String) {
    object SignInScreen: Screen("Logar")
    object ForgotPasswordScreen: Screen("Esqueceu a senha?")
    object SignUpScreen: Screen("Inscrever-se")
    object VerifyEmailScreen: Screen("Verify email")
    object ProfileScreen: Screen("Perfil")
    object HomeScreen: Screen("Mapa")
    object StationListScreen: Screen("Lista de Estacoes")
}