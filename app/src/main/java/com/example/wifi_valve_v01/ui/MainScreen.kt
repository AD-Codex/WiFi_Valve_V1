package com.example.wifi_valve_v01.ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.wifi_valve_v01.components.AppTopBar
import com.example.wifi_valve_v01.components.BottomNavigationBar
import com.example.wifi_valve_v01.navigation.AppNavHost
import com.example.wifi_valve_v01.navigation.BottomNavItem
import com.example.wifi_valve_v01.navigation.Screens
import com.example.wifi_valve_v01.ui.user.LoginDialog
import com.example.wifi_valve_v01.ui.user.UserViewModel


@Composable
fun MainScreen(
    navController: NavHostController,
    userViewModel: UserViewModel = viewModel()) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    val user by userViewModel.user.collectAsState()
    val isLoggedIn = user.id != -1
    val currentUsername = user.username
    var showLoginDialog by remember { mutableStateOf(false) }


    // Auto logged in and Run ping test once on load
    LaunchedEffect(Unit) {
        userViewModel.checkLoginStatus()
    }


    val bottomNavItems = listOf(
        BottomNavItem("Home", Screens.Home.route, Icons.Default.Home),
        BottomNavItem("User", Screens.User.route, Icons.Default.Person),
        BottomNavItem("About", Screens.About.route, Icons.Default.Info)
    )

    Scaffold(
        topBar = {
            AppTopBar(
                onLoginClick = { showLoginDialog = true },
                isLoggedIn = isLoggedIn
            )
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

            if (showLoginDialog) {
                LoginDialog(
                    onDismiss = { showLoginDialog = false },
                    onLogin = { username, password, onResult ->
                        userViewModel.login(username, password, onResult)
                    },
                    isLoggedIn = isLoggedIn,
                    currentUsername = currentUsername,
                    onLogout = {
                        userViewModel.logout()
                        userViewModel.refreshUser()
                    }
                )
            }
        }
    }

}