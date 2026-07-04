package com.demo.mcqquiz.presentation.navigation

sealed class Screen(
    val route: String
) {
    data object Splash : Screen("splash")
    data object Quiz : Screen("quiz")
    data object Result : Screen("result")
}
