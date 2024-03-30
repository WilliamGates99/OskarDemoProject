package com.xeniac.oskardemoproject.core.ui.navigation

sealed class Screen(val route: String) {
    data object LoginScreen : Screen(route = "login_screen")
    data object RegisterScreen : Screen(route = "register_screen")

    data object HomeScreen : Screen(route = "home_screen")

    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}