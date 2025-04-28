package com.example.wifi_valve_v01.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wifi_valve_v01.models.Product


@Composable
fun HomeScreen() {
    Text(
        text = "Home page",
        modifier = Modifier.fillMaxSize()
    )
    FAB()
}

@Preview
@Composable
fun HomeScreenPreview() {
//    HomeScreen()
    FAB()
}

@Composable
fun FAB(){

    var showAddDialog by remember { mutableStateOf(false) }
    var products by remember { mutableStateOf(emptyList<Product>()) }

    Box(modifier = Modifier .fillMaxSize()){
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            onClick = {
                showAddDialog = true
            }

        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Product")
        }
    }

    if (showAddDialog) {
        AddProductDialog(
            onDismiss = { showAddDialog = false},
            onAddProduct = { product ->
                products = products + product
                showAddDialog = false
            }
        )
    }
}