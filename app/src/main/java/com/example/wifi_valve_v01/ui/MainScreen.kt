package com.example.wifi_valve_v01.ui

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.wifi_valve_v01.components.AppTopBar
import com.example.wifi_valve_v01.components.BottomNavigationBar
import com.example.wifi_valve_v01.navigation.AppNavHost
import com.example.wifi_valve_v01.navigation.BottomNavItem
import com.example.wifi_valve_v01.navigation.Screens



@Composable
fun MainScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        BottomNavItem("Home", Screens.Home.route, Icons.Default.Home),
        BottomNavItem("User", Screens.User.route, Icons.Default.Person),
        BottomNavItem("About", Screens.About.route, Icons.Default.Info)
    )

    Scaffold(
        topBar = {
            AppTopBar()
        },
        bottomBar = {
            if (currentRoute in bottomNavItems.map {it.route }) {
                BottomNavigationBar(
                    navController = navController,
                    items = bottomNavItems,
                    currentRoute = currentRoute
                )
            }
        }
    ) { innerPadding  ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AppNavHost(navController = navController)
        }
    }

}