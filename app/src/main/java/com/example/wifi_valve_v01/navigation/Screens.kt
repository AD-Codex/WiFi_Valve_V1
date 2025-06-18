package com.example.wifi_valve_v01.navigation

sealed class Screens(val route: String) {
    object Home : Screens("home")
    object User : Screens("user")
    object About : Screens("about")
    object ProductDetail : Screens("productDetail")
    object ValveDetail : Screens("valveDetail")
}