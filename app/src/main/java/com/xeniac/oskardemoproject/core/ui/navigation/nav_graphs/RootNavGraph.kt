package com.xeniac.oskardemoproject.core.ui.navigation.nav_graphs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.xeniac.oskardemoproject.core.ui.navigation.Screen
import com.xeniac.oskardemoproject.feature_home.presentation.HomeScreen

@Composable
fun SetupRootNavGraph(
    rootNavController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = rootNavController,
        startDestination = startDestination,
        route = NavGraphs.ROUTE_ROOT
    ) {
        authNavGraph(rootNavController = rootNavController)

        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                onLogoutNavigate = {
                    rootNavController.navigate(NavGraphs.ROUTE_AUTH) {
                        popUpTo(Screen.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}

object NavGraphs {
    const val ROUTE_ROOT = "nav_graph_root"
    const val ROUTE_AUTH = "nav_graph_auth"
}