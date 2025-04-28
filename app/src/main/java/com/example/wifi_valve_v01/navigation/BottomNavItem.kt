package com.example.wifi_valve_v01.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem (
    val name: String,
    val route: String,
    val icon: ImageVector,
    val badgeCount: Int = 0
)