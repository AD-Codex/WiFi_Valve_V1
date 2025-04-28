package com.example.wifi_valve_v01.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.wifi_valve_v01.navigation.BottomNavItem


@Composable
fun BottomNavigationBar(
    navController: NavController,
    items: List<BottomNavItem>,
    currentRoute: String?
) {
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.name)},
                label = { Text(item.name)},
                selected = currentRoute == item.route,
                onClick = {
                    // Avoid multiple copies of the same destination
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            // Avoid multiple copies of the same destination
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            // Avoid multiple copies when reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                }

            )
        }
    }


}