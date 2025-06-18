package com.example.wifi_valve_v01.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wifi_valve_v01.models.ProductItem
import com.example.wifi_valve_v01.ui.about.AboutScreen
import com.example.wifi_valve_v01.ui.home.HomeScreen
import com.example.wifi_valve_v01.ui.user.UserScreen
import com.example.wifi_valve_v01.ui.details.ProductDetailScreen
import com.example.wifi_valve_v01.ui.details.ValveDetailScreen


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route,
        modifier = Modifier
    ) {
        composable(Screens.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screens.User.route) {
            UserScreen()
        }
        composable(Screens.About.route) {
            AboutScreen()
        }
        composable(Screens.ProductDetail.route) { backStackEntry ->
            val productItem = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<ProductItem>("productItem")

            if (productItem != null) {
                ProductDetailScreen(productItem = productItem)
            }
        }
        composable(Screens.ValveDetail.route) { backStackEntry ->
            val productItem = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<ProductItem>("productItem")
            if (productItem != null) {
                ValveDetailScreen(productItem = productItem)
            }
        }

    }

}