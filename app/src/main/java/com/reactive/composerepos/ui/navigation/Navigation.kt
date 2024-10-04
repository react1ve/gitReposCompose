package com.reactive.composerepos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.reactive.composerepos.ui.screens.details.DetailsScreen
import com.reactive.composerepos.ui.screens.home.HomeScreen
import com.reactive.composerepos.ui.screens.search.SearchScreen

@Composable
fun Navigation(navController : NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        addHome(navController)
        addSearch(navController)
        addDetails(navController)
    }
}

private fun NavGraphBuilder.addHome(
    navController : NavController,
) {
    composable(route = Screen.Home.route) {
        HomeScreen(
            onDetails = { repo -> navController.navigate(Screen.Details.createRoute(repo)) },
            onSearch = { navController.navigate(Screen.Search.route) }
        )
    }
}

private fun NavGraphBuilder.addSearch(
    navController : NavController,
) {
    composable(route = Screen.Search.route) {
        SearchScreen(
            onDetails = { repo -> navController.navigate(Screen.Details.createRoute(repo)) },
            onNavigateUp = { navController.navigateUp() }
        )
    }
}

private fun NavGraphBuilder.addDetails(
    navController : NavController,
) {
    composable(
        route = Screen.Details.route,
        arguments = listOf(
            navArgument("owner") {
                type = NavType.StringType
            },
            navArgument("name") {
                type = NavType.StringType
            },
        )
    ) {
        DetailsScreen(navigateUp = {
            navController.navigateUp()
        })
    }
}
