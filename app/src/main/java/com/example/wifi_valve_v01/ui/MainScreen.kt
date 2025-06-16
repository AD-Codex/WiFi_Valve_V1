package com.example.wifi_valve_v01.ui

import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.wifi_valve_v01.components.AppTopBar
import com.example.wifi_valve_v01.components.BottomNavigationBar
import com.example.wifi_valve_v01.data.ApiClient.authApiService
import com.example.wifi_valve_v01.data.auth.AuthRepository
import com.example.wifi_valve_v01.models.User
import com.example.wifi_valve_v01.navigation.AppNavHost
import com.example.wifi_valve_v01.navigation.BottomNavItem
import com.example.wifi_valve_v01.navigation.Screens
import com.example.wifi_valve_v01.ui.user.LoginDialog
import com.example.wifi_valve_v01.utils.TokenManager
import kotlinx.coroutines.launch


@Composable
fun MainScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val context = LocalContext.current
    val tokenManager = remember { TokenManager(context) }
    val authRepository = remember { AuthRepository(authApiService, tokenManager) }
    val coroutineScope = rememberCoroutineScope()

    var isLoggedIn by remember { mutableStateOf(false) }
    var currentUsername by remember { mutableStateOf<String?>(null) }
    var showLoginDialog by remember { mutableStateOf(false) }


    // Auto logged in and Run ping test once on load
    LaunchedEffect(Unit) {
        val token = tokenManager.getToken()
        val user = tokenManager.getUser()

        if (!token.isNullOrEmpty() && user.id != -1) {
            try {
                val response = authApiService.ping()
                if (response.isSuccessful) {
                    Log.d("AutoLogin", "Ping successful")
                    isLoggedIn = true
                    currentUsername = user.username
                } else {
                    Log.e("AutoLogin", "Ping failed: ${response.code()}")
                    isLoggedIn = false
                    currentUsername = null
                }
            } catch (e: Exception) {
                Log.e("AutoLogin", "Ping exception", e)
                isLoggedIn = false
                currentUsername = null
            }
        } else {
            Log.d("AutoLogin", "No token or invalid user")
            isLoggedIn = false
            currentUsername = null
        }
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
                        coroutineScope.launch {
                            val result = authRepository.login(username, password)
                            isLoggedIn = result.isSuccess
                            if (result.isSuccess) {
                                val user = tokenManager.getUser()
                                currentUsername = user.username
                            }
                            onResult(result.isSuccess)
                        }
                    },
                    isLoggedIn = isLoggedIn,
                    currentUsername = currentUsername,
                    onLogout = {
                        tokenManager.clearToken()
                        tokenManager.clearUser()
                        isLoggedIn = false
                        currentUsername = null
                    }
                )
            }
        }
    }

}