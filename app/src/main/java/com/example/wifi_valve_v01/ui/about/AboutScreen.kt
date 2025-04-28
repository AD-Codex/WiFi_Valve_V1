package com.example.wifi_valve_v01.ui.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AboutScreen() {
    Text(
        text = "About Us",
        modifier = Modifier.fillMaxSize()
    )
}

@Preview
@Composable
fun AboutScreenPreview() {
    AboutScreen()
}