package com.example.wifi_valve_v01.ui.user

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun UserScreen() {
    Text(
        text = "User Profile",
        modifier = Modifier.fillMaxSize()
    )
}

@Preview
@Composable
fun UserScreenPreview() {
    UserScreen()
}