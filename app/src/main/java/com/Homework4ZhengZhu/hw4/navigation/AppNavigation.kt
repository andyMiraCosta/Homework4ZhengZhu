package com.Homework4ZhengZhu.hw4.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.Homework4ZhengZhu.hw4.views.screen.FavoritePetsScreen
import com.Homework4ZhengZhu.hw4.views.screen.PetDetailsScreen
import com.Homework4ZhengZhu.hw4.views.screen.PetsScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Composable
fun AppNavigation(
    contentType: ContentType,
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.BitcoinsScreen.route
    ) {
        composable(Screens.BitcoinsScreen.route) {
            PetsScreen(
                onBitcoinClicked = { bitcoin ->
                    navHostController.navigate(
                        "${Screens.BitcoinDetailsScreen.route}/${Json.encodeToString(bitcoin)}"
                    )
                },
                contentType = contentType
            )
        }
        composable(
            route = "${Screens.BitcoinDetailsScreen.route}/{bitcoin}",
            arguments = listOf(
                navArgument("bitcoin") {
                    type = NavType.StringType
                }
            )
        ) {
            PetDetailsScreen(
                onBackPressed = {
                    navHostController.popBackStack()
                },
                bitcoin = Json.decodeFromString(it.arguments?.getString("bitcoin") ?: "")
            )
        }
        composable(Screens.FavoriteBitcoinsScreen.route) {
            FavoritePetsScreen()
        }
    }
}