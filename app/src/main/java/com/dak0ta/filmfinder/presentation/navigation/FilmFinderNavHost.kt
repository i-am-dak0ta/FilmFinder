package com.dak0ta.filmfinder.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dak0ta.filmfinder.presentation.ui.screen.FilmDetailsScreen
import com.dak0ta.filmfinder.presentation.ui.screen.FilmListScreen

sealed class Screen(val route: String) {
    data object FilmList : Screen("film_list")

    data object FilmDetails : Screen("film_details/{filmId}") {
        fun createRoute(filmId: Int) = "film_details/$filmId"
        val arguments = listOf(navArgument("filmId") { type = NavType.IntType })
    }
}


@Composable
fun FilmFinderNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.FilmList.route,
        modifier = modifier
    ) {
        composable(route = Screen.FilmList.route) {
            FilmListScreen(
                onFilmClick = { filmId ->
                    navController.navigate(Screen.FilmDetails.createRoute(filmId))
                }
            )
        }

        composable(
            route = Screen.FilmDetails.route,
            arguments = Screen.FilmDetails.arguments
        ) { backStackEntry ->
            val filmId = requireNotNull(backStackEntry.arguments?.getInt("filmId")) {
                "filmId is required for FilmDetailsScreen"
            }
            FilmDetailsScreen(filmId = filmId)
        }
    }
}