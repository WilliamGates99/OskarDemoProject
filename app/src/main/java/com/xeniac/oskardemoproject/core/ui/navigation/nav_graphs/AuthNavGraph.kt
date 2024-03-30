package com.xeniac.oskardemoproject.core.ui.navigation.nav_graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.xeniac.oskardemoproject.core.ui.navigation.Screen
import com.xeniac.oskardemoproject.feature_auth.presentation.login.LoginScreen
import com.xeniac.oskardemoproject.feature_auth.presentation.register.RegisterScreen

fun NavGraphBuilder.authNavGraph(
    rootNavController: NavHostController
) {
    navigation(
        startDestination = Screen.LoginScreen.route,
        route = NavGraphs.ROUTE_AUTH
    ) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(
                onNavigateToRegister = {
                    rootNavController.navigate(Screen.RegisterScreen.route)
                },
                onNavigateToHomeScreen = {
                    rootNavController.navigate(Screen.HomeScreen.route) {
                        popUpTo(NavGraphs.ROUTE_AUTH) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(
                onNavigateUp = rootNavController::navigateUp,
                onNavigateToHomeScreen = {
                    rootNavController.navigate(Screen.HomeScreen.route) {
                        popUpTo(NavGraphs.ROUTE_AUTH) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}